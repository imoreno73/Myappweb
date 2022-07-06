package es.cex.common.thymeleaf.dateUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import es.cex.common.utils.DateUtils;

public class DateUtilsFactory implements IExpressionObjectFactory {

	public static final String DATE_UTILS_EVALUATION_VARIABLE_NAME = "dateUtils";
	private static final Set<String> ALL_EXPRESSION_OBJECT_NAMES = Collections
			.unmodifiableSet(new HashSet<>(Arrays.asList(DATE_UTILS_EVALUATION_VARIABLE_NAME)));

	@Override
	public Set<String> getAllExpressionObjectNames() {
		return ALL_EXPRESSION_OBJECT_NAMES;
	}

	@Override
	public Object buildObject(IExpressionContext context, String expressionObjectName) {
		if (DATE_UTILS_EVALUATION_VARIABLE_NAME.equals(expressionObjectName)) {
			return new DateUtils();
		}
		return null;
	}

	@Override
	public boolean isCacheable(String expressionObjectName) {
		return true;
	}

}
