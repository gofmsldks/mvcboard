import com.doubles.mvcboard.article.domain.ArticleVO;
import com.doubles.mvcboard.article.persistence.ArticleDAO;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.List;

public class BoardDAOTest {
    @Inject
    private ArticleDAO articleDAO;
    private static Logger logger = LoggerFactory.getLogger(BoardDAOTest.class);

    @Test
    public void testCreate() throws Exception {
        for (int i = 1; i <= 1000; i++) {
            ArticleVO articleVO = new ArticleVO();
            articleVO.setTitle(i + "번째 글 제목입니다...");
            articleVO.setContent(i + "번재 글 내용입니다...");
            articleVO.setWriter("user0" + (i % 10));

            articleDAO.create(articleVO);
        }
    }

    @Test
    public void testListPaging() throws Exception {

        int page = 3;
        List<ArticleVO> articles = articleDAO.listPaging(page);
        for (ArticleVO article : articles) {
            logger.info(article.getArticleNo() + ":" + article.getTitle());
        }
    }
}