package hire.me.controller.filters;

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

	public void init(FilterConfig config) throws ServletException {
		logger.trace("Initialization for Access filter");

		allowedPages.put(UserRole.GUEST,
				Stream.of("registration",
						"app",
						"login",

						"home",
						"to_home_page",
						"to_catalog_page",
						"to_registration_page",
						"to_support_page")
				.collect(collectingAndThen(toCollection(HashSet::new), Collections::unmodifiableSet)));

		allowedPages.put(UserRole.SUBSCRIBER,
				Stream.of("createSubscription",
						"subscriptions",
						"home",
						"to_home_page",
						"logout",
						"subscriberAccount",
						"to_catalog_page",
						"",
						"to_support_page")
						.collect(collectingAndThen(toCollection(HashSet::new), Collections::unmodifiableSet)));

		allowedPages.put(UserRole.ADMIN,
				Stream.of("registration",
						"home",
						"to_home_page",
						"logout",
						"administratorAccount",
						"",
						"to_subscribers_page",
						"to_catalog_page",
						"to_block_unblock_user",
						"to_edit_periodic",
						"to_make_order_nonorder_periodic",
						"to_support_page")
						.collect(collectingAndThen(toCollection(HashSet::new), Collections::unmodifiableSet)));

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		logger.trace("URL is " + req.getRequestURI());

		String path = req.getRequestURI()
				.replace(req.getContextPath(), "")
				.replace(req.getServletPath(), "")
				.replace("/", "");

		logger.trace("URL is " + path);

		logger.trace("current role before -> " + (UserRole) req.getSession().getAttribute("role"));

		if (req.getSession().getAttribute("role") == null) {
			req.getSession().setAttribute("role", UserRole.GUEST);
		}
		UserRole currentRole = ((UserRole) req.getSession().getAttribute("role"));
		logger.trace("current role " + currentRole + ", path = " + path);
		logger.trace("allowedPages.get(currentRole).contains(path) => " + allowedPages.get(currentRole).contains(path));

		if(allowedPages.get(currentRole).contains(path)) {
			logger.trace("send info to next element");
			logger.trace("allowedPages.get(currentRole).contains(path) = " + allowedPages.get(currentRole).contains(path));
			chain.doFilter(req, resp);
		} else {
			if(currentRole.equals(UserRole.GUEST)) {
				logger.trace("forward quest to home page");
				req.getRequestDispatcher("/WEB-INF/view/home_page.jsp").forward(req, resp);
			} else {
				logger.trace("forward quest to error 403");
				req.getRequestDispatcher("/WEB-INF/common/error/403.jsp").forward(req, resp);
			}
		}
	}

	public void destroy() {
	}
}
