package nl.captcha.servlet;

import static nl.captcha.Captcha.NAME;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import nl.captcha.Captcha;


/**
 * Builds a 200x50 (default) CAPTCHA and attaches it to the session. This is intended to help prevent
 * bots from simply reloading the page and getting new images until one is
 * generated which they can successfully parse. Removal of the session attribute
 * <code>CaptchaServletUtil.NAME</code> will force a new <code>Captcha</code>
 * to be added to the session.
 * 
 * <p>Image height and width may be customized by adding the "heigh" and "width" init-params to
 * web.xml. For example:</p>
 * <p><pre>    &lt;servlet&gt;        
  	&lt;servlet-name&gt;StickyCaptcha&lt;/servlet-name&gt;
        &lt;servlet-class&gt;nl.captcha.servlet.StickyCaptchaServlet&lt;/servlet-class&gt;
        &lt;init-param&gt;
            &lt;param-name&gt;width&lt;/param-name&gt;
            &lt;param-value&gt;250&lt;/param-value&gt;
        &lt;/init-param&gt;
        &lt;init-param&gt;
            &lt;param-name&gt;height&lt;/param-name&gt;
            &lt;param-value&gt;75&lt;/param-value&gt;
        &lt;/init-param&gt;
    &lt;/servlet&gt;
</pre></p>

 * 
 * @author <a href="mailto:james.childers@gmail.com">James Childers</a>
 * 
 */
public class StickyCaptchaServlet extends SimpleCaptchaServlet {

    private static final long serialVersionUID = 40913456229L;
    
    /**
     * Write out the CAPTCHA image stored in the session. If not present,
     * generate a new <code>Captcha</code> and write out its image.
     * 
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Captcha captcha;
        if (session.getAttribute(NAME) == null) {
            captcha = new Captcha.Builder(_width, _height)
            	.addText()
            	.gimp()
            	.addBorder()
                .addNoise()
                .addBackground()
                .build();

            session.setAttribute(NAME, captcha);
            CaptchaServletUtil.writeImage(resp, captcha.getImage());

            return;
        }

        captcha = (Captcha) session.getAttribute(NAME);
        CaptchaServletUtil.writeImage(resp, captcha.getImage());
    }
}
