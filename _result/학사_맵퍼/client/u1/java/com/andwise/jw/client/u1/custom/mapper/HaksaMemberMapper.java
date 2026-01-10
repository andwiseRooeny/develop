package com.andwise.jw.client.u1.custom.mapper;

import java.util.Map;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;

/**
 * 유원대학교 학사 시스템 연동 매퍼
 * 연관 레퍼런스: _Reference_For_Java_Dev.md, member_integration_details.md
 */
@Mapper("haksaMemberMapper")
public interface HaksaMemberMapper {

    /**
     * 학사 로그인 인증 (YTIS_SYSTEM.CHECK_EN_PASSWORD)
     * 
     * @param params (id, password)
     * @return String (YES / NO)
     */
    public String checkLogin(Map<String, Object> params);

    /**
     * 학사 회원 상세 정보 조회 (YTIS_HAKSA.VIEW_GONET_V_YDUAUTH)
     * 
     * @param params (id)
     * @return Map (ID, NAME, SOSOKNAME, GBSINBUN 등)
     */
    public Map<String, Object> selectMemberInfo(Map<String, Object> params);

}
