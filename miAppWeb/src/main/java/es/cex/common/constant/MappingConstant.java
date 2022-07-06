package es.cex.common.constant;

/**
 * Class with the available url mappings
 *
 */
public final class MappingConstant {

	/** USER_ROOT */
	public static final String USER_ROOT = "/users";

	/** USER_SEARCH */
	public static final String USER_SEARCH = "/search";

	/** USER_DETAIL */
	public static final String USER_DETAIL = "/{login}";

	/** USER_IMPORT */
	public static final String USER_IMPORT = "/import/{login}";

	/** USER_NEW */
	public static final String USER_NEW = "/new";

	/** USER_CREATE */
	public static final String USER_CREATE = "/create";

	/** USER_EDIT */
	public static final String USER_EDIT = "/edit/{login}";

	/** USER_UPDATE */
	public static final String USER_UPDATE = "/update/{login}";

	/** USER_DELETE */
	public static final String USER_DELETE = "/delete/{id}";

	/** ROLE_ROOT */
	public static final String ROLE_ROOT = "/roles";

	/** ROLE_SEARCH */
	public static final String ROLE_SEARCH = "/search";

	/** ROLE_SEARCH_BY_USERS_ID */
	public static final String ROLE_SEARCH_BY_USERS_ID = "/searchByUsers";

	/** ROLE_SEARCH_BY_FUNCTIONAL_GROUP */
	public static final String ROLE_SEARCH_BY_FUNCTIONAL_GROUP = "/searchByFunctionalGroup";

	/** ROLE_DETAIL */
	public static final String ROLE_DETAIL = "/{slug}";

	/** ROLE_NEW */
	public static final String ROLE_NEW = "/new";

	/** ROLE_CREATE */
	public static final String ROLE_CREATE = "/create";

	/** ROLE_EDIT */
	public static final String ROLE_EDIT = "/edit/{slug}";

	/** ROLE_UPDATE */
	public static final String ROLE_UPDATE = "/update/{slug}";

	/** ROLE_DELETE */
	public static final String ROLE_DELETE = "/delete/{id}";

	/** ROLE_ROOT */
	public static final String FUNCTIONAL_GROUP_ROOT = "/functionalGroups";

	/** FUNCTIONAL_GROUP_ROOT */
	public static final String FUNCTIONAL_GROUP_LIST = "/list";

	/** FUNCTIONAL_GROUP_SEARCH */
	public static final String FUNCTIONAL_GROUP_SEARCH = "/search";

	/** FUNCTIONAL_GROUP_DETAIL */
	public static final String FUNCTIONAL_GROUP_DETAIL = "/{slug}";

	/** FUNCTIONAL_GROUP_NEW */
	public static final String FUNCTIONAL_GROUP_NEW = "/new";

	/** FUNCTIONAL_GROUP_CREATE */
	public static final String FUNCTIONAL_GROUP_CREATE = "/create";

	/** FUNCTIONAL_GROUP_EDIT */
	public static final String FUNCTIONAL_GROUP_EDIT = "/edit/{slug}";

	/** FUNCTIONAL_GROUP_UPDATE */
	public static final String FUNCTIONAL_GROUP_UPDATE = "/update/{slug}";

	/** FUNCTIONAL_GROUP_DELETE */
	public static final String FUNCTIONAL_GROUP_DELETE = "/delete/{id}";

	/** PAGE_ROOT */
	public static final String PAGE_ROOT = "/pages";

	/** PAGE_LIST */
	public static final String PAGE_LIST = "/list";

	/** PAGE_ITEM */
	public static final String PAGE_DETAIL = "/{id}";

	/** PAGE_DELETE */
	public static final String PAGE_DELETE = "/delete/{id}";


	/**
	 * Avoid class instance
	 */
	private MappingConstant() {}

}
