package kr.co.gcInside.controller;

import kr.co.gcInside.service.AdminService;
import kr.co.gcInside.vo.MemberVO;
import kr.co.gcInside.vo.TermsVO;
import kr.co.gcInside.vo.gall_cate2VO;
import kr.co.gcInside.vo.galleryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 2023/03/10 // 심규영 // 관리자 컨트롤러 생성
 */
@Controller
public class AdminController {

    /**
     * 2023/03/10 // 심규영 // 관리자 서비스 연결
     */
    @Autowired
    private AdminService service;

    /**
     * 2023/03/10 // 심규영 // 관리자 메인 인덱스 get맵핑
     */
    @GetMapping("admin/index")
    public String index() {
        return "admin/index";
    }

    /**
     * 2023/03/10 // 심규영 // 관리자 멤버 목록 및 검색 페이지 get맵핑
     * 2023/03/16 // 라성준 // 관리자 멤버 불러오기
     * @return
     */
    @GetMapping("admin/member/search")
    public String memberSearch(Model model) {
        List<MemberVO> search = service.SearchMember();
        model.addAttribute("search", search);
        return "admin/member/search";
    }

    /**
     * 2023/03/10 // 심규영 // 관리자 약관 설정 페이지 get 맵핑
     * @return
     */
    @GetMapping("admin/config/terms")
    public String configTerms(Model model) {
        List<TermsVO> terms = service.selectTerms();

        model.addAttribute("terms", terms);

        return "admin/config/terms";
    }

    /**
     * 2023/03/10 // 심규영 // 관리자 약관 업데이트 post 맵핑
     *      들어오는 값
     *      type    => 약관 번호
     *      content => 약관 내용
     * @param data
     */
    @ResponseBody
    @PostMapping("admin/config/updateTerm")
    public Map<String, Object> updateTerm(@RequestBody Map<String, String> data) {
        int result = 0;

        result = service.updateTerms(data.get("type"), data.get("content"));

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", result);

        return resultMap;
    }

    /**
     * 2023/03/10 // 김재준 // 관리자 메인갤러리 생성 get 매핑
     * @return
     */
    @GetMapping("admin/gallery/create_main")
    public String createMainGallery (Model model) {

        List<gall_cate2VO> cates = service.selectGalleryCates();

        model.addAttribute("cates", cates);

        return "admin/gallery/create_main";
    }

    @PostMapping("admin/gallery/create_main")
    public String createMainGallery (HttpServletRequest req, Model model, galleryVO vo) {
        /* 관리자 userEntity는 추후 추가*/

        vo.setGell_cate(req.getParameter("gell_cate"));
        vo.setGell_name(req.getParameter("gell_name"));
        vo.setGell_address(req.getParameter("gell_address"));
        vo.setGell_info(req.getParameter("gell_info"));

        service.createMainGallery(vo);

        model.addAttribute("msg", "갤러리가 생성되었습니다.");

        return "redirect:/admin/index";
    }
}
