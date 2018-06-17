package hello.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class TokenFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String accessToken = request.getHeader("Access-Token");
        if (StringUtils.isEmpty(accessToken)) {
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(400);
            context.setResponseBody("Access-Token is required.");
            context.set("isSuccess", false);
        } else {
            // compare token
            context.setSendZuulResponse(true);
            context.setResponseStatusCode(200);
            context.set("isSuccess", true);
        }
        return null;
    }
}
