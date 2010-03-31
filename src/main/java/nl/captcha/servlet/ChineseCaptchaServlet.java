package nl.captcha.servlet;

import static nl.captcha.Captcha.NAME;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.text.producer.ChineseTextProducer;

/**
 * Generate a CAPTCHA image/answer pair using Chinese characters.
 * 
 * @author <a href="james.childers@gmail.com">James Childers</a>
 *
 */
public class ChineseCaptchaServlet extends SimpleCaptchaServlet {

	private static final long serialVersionUID = -66324012009340831L;

	@Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Captcha captcha;
        if (session.getAttribute(NAME) == null) {
	        captcha = new Captcha.Builder(_width, _height)
	            	.addText(new ChineseTextProducer())
	            	.gimp()
	            	.addBorder()
	                .addNoise()
	                .addBackground(new GradiatedBackgroundProducer())
	                .build();

	        session.setAttribute(NAME, captcha);
	        CaptchaServletUtil.writeImage(resp, captcha.getImage());
	        
	        return;
        }

        captcha = (Captcha) session.getAttribute(NAME);
        CaptchaServletUtil.writeImage(resp, captcha.getImage());
    }
}