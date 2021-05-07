package hire.me.controller.filters;

import hire.me.model.entity.account.Account;
import hire.me.model.entity.account.User;
import hire.me.model.entity.account.UserRole;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

public class AccessFilter implements Filter {

	private static final Logger logger = LogManager.getLogger(AccessFilter.class);

	private Map<UserRole, Set<String>> allowedPages = new HashMap<>();

	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("Initialization for Access filter");

		allowedPages.put(UserRole.GUEST,
				Stream.of("/app/registration",
						"/app/login",
						"/app/home")
				.collect(collectingAndThen(toCollection(HashSet::new), Collections::unmodifiableSet)));

		allowedPages.put(UserRole.SUBSCRIBER,
				Stream.of("/app/createSubscription",
						"/app/subscriptions",
						"/app/home",
						"/app/logout",
						"/app/subscriberAccount",
						"")
						.collect(collectingAndThen(toCollection(HashSet::new), Collections::unmodifiableSet)));

		allowedPages.put(UserRole.ADMIN,
				Stream.of("/app/registration",
						"/app/home",
						"/app/logout",
						"/app/administratorAccount",
						"",
						"/app/subscribers")
						.collect(collectingAndThen(toCollection(HashSet::new), Collections::unmodifiableSet)));

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		logger.info("URL is " + req.getRequestURI());

		String path = req.getRequestURI()
				.replace(req.getContextPath(), "")
				.replace(req.getServletPath(), "")
				.replace("/", "");

		logger.info("URL is " + path);

		if (req.getSession().getAttribute("role") == null) {
			req.getSession().setAttribute("role", UserRole.GUEST);
		}
		UserRole currentRole = ((UserRole) req.getSession().getAttribute("role"));
		logger.info("current role " + currentRole);

		if(allowedPages.get(currentRole).contains(path)) {
			logger.info("send info to next element");
			chain.doFilter(req, resp);
		} else {
			if(currentRole.equals(UserRole.GUEST)) {
				logger.info("forward quest to login");
				req.getRequestDispatcher("/WEB-INF/common/login.jsp").forward(req, resp);
			} else {
				logger.info("forward quest to error 403");
				req.getRequestDispatcher("/WEB-INF/common/error/403.jsp").forward(req, resp);
			}
		}
	}

	public void destroy() {
	}
}
