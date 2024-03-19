package com.hs.drpaper.restcontroller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.drpaper.dao.GroupInfoDao;
import com.hs.drpaper.dao.GroupMemberDao;
import com.hs.drpaper.dao.GroupsDao;
import com.hs.drpaper.dto.GroupMemberDto;
import com.hs.drpaper.dto.GroupTagDto;
import com.hs.drpaper.dto.GroupsDto;
import com.hs.drpaper.vo.CreateGroupRequestVO;
import com.hs.drpaper.vo.GroupInfoReqeustVO;
import com.hs.drpaper.vo.GroupJoinRequestVO;
import com.hs.drpaper.vo.GroupResponseVO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/groups")
@CrossOrigin
@Slf4j
@Tag(name = "group")
public class GroupsRestController {
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private GroupsDao groupsDao;
	@Autowired
	private GroupInfoDao groupInfoDao;
	@Autowired
	private GroupMemberDao groupMemberDao;

	@PostMapping("/createGroup")
	public ResponseEntity<?> createGroup(@RequestBody CreateGroupRequestVO createGroupRequestVO) {

		int nextNum = groupsDao.sequence();

		GroupsDto groupsDto = createGroupRequestVO.getGroupsDto();
		GroupTagDto groupTagDto = createGroupRequestVO.getGroupTagDto();

		// 비공개라면 비밀번호 암호화
		if (groupsDto.getGroupsPw() != null) {
			String originPw = groupsDto.getGroupsPw();
			String encodedPw = encoder.encode(originPw);
			groupsDto.setGroupsPw(encodedPw);
		}
		// 그룹넣고
		groupsDao.createGroup(groupsDto);

		// 그룹에 호스트 넣고
		groupMemberDao.addMember(nextNum, groupsDto.getGroupsHost());

		// 태그 검색해서 있으면 있는거로 없으면 새로 생성

		groupInfoDao.addInfo(nextNum);

		return ResponseEntity.ok(null);
	}

	@GetMapping("/groupList")
	public List<GroupResponseVO> groupList() {

		return groupsDao.groupList();
	}

	@GetMapping("/groupPreView/{groupsId}")
	public GroupInfoReqeustVO groupPreView(@PathVariable int groupsId) {
		return groupsDao.getGroupInfo(groupsId);
	}

	/// 그룹에 가입하기

	@PostMapping("/joinGroup")
	public ResponseEntity<?> joinGroup(@RequestBody GroupJoinRequestVO groupJoinRequestVO) {

		// 리스트 불러와서 리스트에 내 아이디 있는지 확인하고
		int groupsId = groupJoinRequestVO.getGroupsId();
		List<GroupMemberDto> memberList = groupMemberDao.memberList(groupsId);
		//이메일 리스트만 따로 만들기 비교용
		ArrayList<String> emailList = new ArrayList<>();
		for(GroupMemberDto member : memberList) {
			String email = member.getClientEmail();
			emailList.add(email);
			log.debug("email{}",email);
		}
		// 비밀방인지 확인 비밀번호가 null이 아니면 비밀번호 매치하는지 확인 VO로 변경해야함
		GroupsDto groupsDto = groupsDao.findGroup(groupsId);
		String dbPw = groupsDto.getGroupsPw();
		String inputPw = groupJoinRequestVO.getGroupsPw();
		
		
		// 그룹정보 불러오기
		GroupInfoReqeustVO groupInfoReqeustVO = groupsDao.getGroupInfo(groupsId);
		int maxMember = groupInfoReqeustVO.getGroupInfoMaxMember();
		int currentMemberCount = memberList.size();
		
		
		///비밀번호가 있으면
		if(dbPw != null) {
			boolean isMatch = encoder.matches( inputPw,dbPw);
			log.debug("is={}",isMatch);
			if(isMatch) {
				//비번 맞으면 
				//이미 가입된 사람이면 리턴
				if (emailList.contains(groupJoinRequestVO.getClientEmail())) {
					return ResponseEntity.internalServerError().build();
				}
				if (currentMemberCount < maxMember) {
					log.debug("member{}, max{}", currentMemberCount,maxMember);
					groupMemberDao.addMember(groupsId, groupJoinRequestVO.getClientEmail());
					log.debug("여기?");
					return ResponseEntity.ok(200);
				} else {
					log.debug("2여기?");
					return ResponseEntity.badRequest().build();
				}
			}else {
				//틀렸으면
				log.debug("3여기?");
				return ResponseEntity.status(502).build();
			}
		}else {
			//비번 없으면
			//이미 가입된 사람이면 리턴
			if (emailList.contains(groupJoinRequestVO.getClientEmail())) {
				log.debug("4여기?");
				log.debug("list={}", emailList);
				return ResponseEntity.internalServerError().build();
			}
			
			if (currentMemberCount < maxMember) {
				log.debug("id={}, email={}", groupsId, groupJoinRequestVO.getClientEmail());

				groupMemberDao.addMember(groupsId, groupJoinRequestVO.getClientEmail());
				return ResponseEntity.ok(200);
			} else {
				log.debug("5여기?member{}, max{}", currentMemberCount,maxMember);
				return ResponseEntity.badRequest().build();
			}
		}
	}
	
	@GetMapping("/myGroup/{clientEmail}")
	public List<GroupInfoReqeustVO> myGroup(@PathVariable String clientEmail){
		return groupsDao.getMygroup(clientEmail);
	}
}
