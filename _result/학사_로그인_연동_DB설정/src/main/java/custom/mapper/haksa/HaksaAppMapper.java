package custom.mapper.haksa;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.egovframe.rte.psl.dataaccess.mapper.Mapper;
import org.springframework.stereotype.Repository;

import com.andwise.jw.app.service.impl.AppMapper;

/**
 * 학사 데이터 접근을 위한 Mapper 클래스 (Advanced Pattern)
 * DB Alias: HAKSA (@HAKSA.JWXE)
 * AppMapper를 상속받으며, 명시적으로 sqlSessionHaksa를 주입받아 사용합니다.
 */
@Repository("haksaAppMapper")
@Mapper
public class HaksaAppMapper extends AppMapper {

    /**
     * 학사 전용 SqlSessionFactory를 명시적으로 주입받아 설정합니다.
     * 
     * @param sqlSession 학사 전용 세션 팩토리
     */
    @Override
    @Resource(name = "sqlSessionHaksa")
    public void setSqlSessionFactory(SqlSessionFactory sqlSession) {
        super.setSqlSessionFactory(sqlSession);
    }

}
