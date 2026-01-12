package custom.mapper.haksa;

import java.util.Map;
import javax.annotation.Resource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.egovframe.rte.psl.dataaccess.util.EgovMap;
import org.springframework.stereotype.Repository;
import com.andwise.jw.app.service.impl.AppMapper;

/**
 * 학사 DB 연동을 위한 AppMapper 클래스
 * member_integration_details.md의 명세를 기반으로 구현되었습니다.
 */
@Repository("haksaAppMapper")
public class HaksaAppMapper extends AppMapper {

    @Override
    @Resource(name = "haksaSqlSession")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

    /**
     * 학사 패스워드 검증 함수 실행
     * 
     * @param param {id: 아이디, pw: 암호}
     * @return YES/NO
     */
    public String checkHaksaPassword(Map<String, Object> param) throws Exception {
        return selectOne("custom.mapper.haksa.HaksaAppMapper.checkHaksaPassword", param);
    }

    /**
     * 학사 회원 신상 정보 조회 (View)
     * 
     * @param id 아이디
     * @return 회원 정보 Map (ID, NAME, SOSOKNAME, GBSINBUN)
     */
    public EgovMap selectHaksaUserInfo(String id) throws Exception {
        return selectOne("custom.mapper.haksa.HaksaAppMapper.selectHaksaUserInfo", id);
    }

    /**
     * 학사 DB 시간 조회 테스트
     */
    public String selectHaksaNow() throws Exception {
        return selectOne("custom.mapper.haksa.HaksaAppMapper.selectHaksaNow", null);
    }

}
