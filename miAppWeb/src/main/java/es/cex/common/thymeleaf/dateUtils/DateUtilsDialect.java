package es.cex.common.thymeleaf.dateUtils;

import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;

public class DateUtilsDialect extends AbstractDialect implements IExpressionObjectDialect {

	private static final IExpressionObjectFactory DATE_UTILS_EXPRESSION_OBJECTS_FACTORY = new DateUtilsFactory();

	public DateUtilsDialect() {
		super(DateUtilsFactory.DATE_UTILS_EVALUATION_VARIABLE_NAME);
	}

	@Override
	public IExpressionObjectFactory getExpressionObjectFactory() {
		return DATE_UTILS_EXPRESSION_OBJECTS_FACTORY;
	}
}