package kr.co.gcInside.service;

import kr.co.gcInside.dao.MainDAO;
import kr.co.gcInside.vo.galleryVO;
import kr.co.gcInside.vo.gell_articleVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MainService {

    @Autowired
    private MainDAO dao;

    //read

    /**
     * 2023.03.22 // 라성준 // 메인 인덱스  메인  신설갤 서비스
     * @return
     */
    public List<galleryVO> MainIndexNewmCommunity (int start) {
        return dao.MainIndexNewCommunity(start);
    }

    /**
     * 2023.03.22 // 라성준 // 메인 인덱스  마이너  신설갤 서비스
     * @return
     */
    public List<galleryVO> MainIndexNewmgellCommunity (int start) {
        return dao.MainIndexNewMgellCommunity(start);
    }

    /**
     * 2023.03.23 // 라성준 // 메인 인덱스  미니 신설갤 서비스
     * @param start
     * @return
     */
    public List<galleryVO> MainIndexNewminiCommunity (int start) {
        return dao.MainIndexNewMiniCommunity(start);
    }

    /**
     * 2023.03.27 // 라성준 // 메인 인덱스 롤링갤러리 서비스
     * @return
     */
    public List<galleryVO> MainIndexRollingGall () {
        return dao.MainIndexRollingGall();
    }

    /**
     * 2023.03.24 // 라성준 // 마이너 갤러리 개수 가져오기
     * @return
     */
    public int MainIndexNewCommunityCount(Map<String, String> data) {
        return dao.MainIndexNewCommunityCount(data);
    }

    /**
     * 2023/04/07 // 김동민 // 개념글(임시로 추천수1이상 글) 출력
     */
    public List<gell_articleVO> selecthotarticle(){

        return dao.selecthotarticle();
    }
    /**
     * 2023/04/07 // 김동민 // 흥한갤러리 관련 minor에서 사용했던 코드 메인에 적용
     */
    public List<galleryVO> selecthotgall() {
        return dao.selecthotgall();
    } /* 흥한 갤러리 불러오기*/
    public List<galleryVO> selectnewgall() {
        return dao.selectnewgall();
    } /* 신설 갤러리 불러오기*/
    public List<galleryVO> rankdiff() {
        List<galleryVO> today = dao.mtodayrank();
        List<galleryVO> yesterday = dao.myesterdayrank();
        List<galleryVO> resultList = new ArrayList<>();

        for (galleryVO yes : yesterday) {
            for (galleryVO to : today) {
                if (yes.getGell_num() == to.getGell_num()) {
                    int diff = yes.getGell_yesterday_rank() - to.getGell_today_rank();
                    galleryVO gelldiff = new galleryVO();
                    gelldiff.setGell_num(yes.getGell_num());
                    gelldiff.setGell_yesterday_rank(yes.getGell_yesterday_rank());
                    gelldiff.setGell_today_rank(to.getGell_today_rank());
                    gelldiff.setGell_rank_diff(diff);
                    resultList.add(gelldiff);
                }
            }
        }
        return resultList;
    } /* 갤러리 랭크차 구하기 */
    public void gallinit(){
        dao.initrank(); /* gc_gell 테이블 랭크 칼럼 초기화 */
        log.info("initrank");
        dao.todayarticlecount(); /* gc_gell_article에서 갤러리별 오늘 게시물 작성수 업데이트*/
        log.info("todayarticleupdate");
        dao.resetrank();/* 쿼리문 변수 rank 초기화 */
        log.info("resetrankyesday");
        dao.updatehotgallyesterdayrank(); /* 어제 게시글 개수 기준으로 어제랭킹 update */
        log.info("updateyesrank");
        dao.resetrank(); /* 쿼리문 변수 rank 초기화 */
        log.info("resetranktoday");
        dao.updatehotgalltodayrank(); /* 오늘 게시글 개수 기준으로 오늘 랭킹 update */
        log.info("updatetorank");

    } /* 불러오기 전 초기 작업 */
}
