package com.doubles.mvcboard.article.controller;

import com.doubles.mvcboard.article.domain.ArticleVO;
import com.doubles.mvcboard.article.service.ArticleService;
import com.doubles.mvcboard.commons.paging.Criteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.inject.Inject;

@Controller
@RequestMapping("/article")
/**
 * @RequestMapping("/article")을 추가함으로써 공통의 경로를 /article로 인식하도록 설정,
 * 보통 컨트롤러는 하나의 기능을 가진 모듈의 대표적인 경로를 갖게함.
 * 이후 컨트롤러에 작성할 메서드들의 매핑URL의 길이를 줄여줌.
 */
public class ArticleController {

    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;

    @Inject
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // 등록 페이지 이동
    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String writeGET() {

        logger.info("normal writeGET() called...");

        return "article/normal/write";
    }

    // 등록 처리

    /**
     * 게시글 등록 처리요청을 처리.
     * redirect되는 시점에 RedirectAttributes객체의 addFlashAttribute()를 이용해
     * 처리가 성공적으로 이루어진 것을 알리기 위해 regSuccess메시지를 String데이터로 저장.
     * @param articleVO
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public String writePOST(ArticleVO articleVO,
                            RedirectAttributes redirectAttributes) throws Exception {

        logger.info("normal writePOST() called...");
        logger.info(articleVO.toString());
        articleService.create(articleVO);
        redirectAttributes.addFlashAttribute("msg", "regSuccess");

        return "redirect:/article/normal/list";
    }

    //목록 페이지 이동
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) throws Exception {

        logger.info("normal list() called ...");
        model.addAttribute("articles", articleService.listAll());

        return "article/normal/list";
    }

    //조회 페이지 이동
    @RequestMapping(value = "/listCriteria", method = RequestMethod.GET)
    public String listCriteria(Model model, Criteria criteria) throws Exception {
        logger.info("normal listCriteria() ...called ");
        model.addAttribute("articles", articleService.listCriteria(criteria));
        return "article/normal/list_criteria";
    }

    // 수정 페이지 이동
    @RequestMapping(value = "/read", method = RequestMethod.GET)
    public String read(@RequestParam("articleNo") int articleNo,
                       Model model) throws Exception {

        logger.info("normal read() called ...");
        model.addAttribute("article", articleService.read(articleNo));

        return "article/normal/read";
    }


    // 수정 페이지 이동
    @RequestMapping(value = "/modify", method = RequestMethod.GET)
    public String modifyGET(@RequestParam("articleNo") int articleNo,
                            Model model) throws Exception {

        logger.info("normal modifyGet() called ...");
        model.addAttribute("article", articleService.read(articleNo));

        return "article/normal/modify";
    }

    // 수정 처리

    /**
     * 게시글 수정 처리요청을 처리.
     * redirect되는 시점에 RedirectAttributes객체의 addFlashAttribute()를 이용해
     * 처리가 성공적으로 이루어진 것을 알리기 위해 modSuccess메시지를 String데이터로 저장.
     * @param articleVO
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public String modifyPOST(ArticleVO articleVO,
                             RedirectAttributes redirectAttributes) throws Exception {

        logger.info("modifyPOST ... called");
        articleService.update(articleVO);
        redirectAttributes.addFlashAttribute("msg", "modSuccess");

        return "redirect:/article/list";
    }

    // 삭제 처리

    /**
     * 게시글 삭제 처리요청을 처리.
     * redirect되는 시점에 RedirectAttributes객체의 addFlashAttribute()를 이용해 처리가 성공적으로
     * 이루어진 것을 알리기 위해 delSuccess메시지를 String데이터로 저장.
     * @param articleNo
     * @param redirectAttributes
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public String remove(@RequestParam("articleNo") int articleNo,
                         RedirectAttributes redirectAttributes) throws Exception {

        logger.info("remove ...");
        articleService.delete(articleNo);
        redirectAttributes.addFlashAttribute("msg", "delSuccess");

        return "redirect:/article/list";
    }

}
