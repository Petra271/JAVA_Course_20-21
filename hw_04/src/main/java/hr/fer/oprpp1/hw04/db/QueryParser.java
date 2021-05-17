package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The class represents a query parser.
 * 
 * @author Petra
 *
 */
public class QueryParser {

	/**
	 * A list of parsed expressions
	 */
	private List<ConditionalExpression> expressions;

	/**
	 * Query lexer
	 */
	private QueryLexer lexer;

	/**
	 * Creates an instance of this class.
	 * 
	 * @param text text that will be parsed
	 */
	public QueryParser(String text) {
		Objects.requireNonNull(text, "The given text musn't be null");
		this.lexer = new QueryLexer(text);
		this.expressions = new ArrayList<>();
		parse();
	}

	/**
	 * Checks if the current query is direct, i.e. that is contains only one
	 * expression in which the field is JMBAG and the operator EQUALS.
	 *
	 * @return <code>true</code> if the current query is direct and
	 *         <code>false</code> otherwise
	 */
	boolean isDirectQuery() {
		if (this.expressions.size() != 1)
			return false;
		return expressions.get(0).getFieldGetter() == FieldValueGetters.JMBAG
				&& expressions.get(0).getComparisonOperator() == ComparisonOperators.EQUALS;
	}

	/**
	 * Returns the jmbag of a direct query.
	 *
	 * @return jmbag of a direct query
	 * @throws {@link IllegalStateException} if query is not direct
	 */
	String getQueriedJMBAG() {
		if (!this.isDirectQuery())
			throw new IllegalStateException("This query is not direct.");
		return expressions.get(0).getStringLiteral();
	}

	/**
	 * Returns the list of parsed expression from the current query.
	 * 
	 * @return the list of parsed expression from the current query
	 */
	List<ConditionalExpression> getQuery() {
		return this.expressions;
	}

	/**
	 * Parsed the current query.
	 */
	private void parse() {

		QueryToken token = lexer.nextToken();

		while (true) {
			if (token.getType() != QueryTokenType.FIELD)
				throw new QueryParserException("The first element of the expression must be a field.");

			IFieldValueGetter fieldGetter = switch (token.getValue()) {
			case "jmbag" -> FieldValueGetters.JMBAG;
			case "firstName" -> FieldValueGetters.FIRST_NAME;
			case "lastName" -> FieldValueGetters.LAST_NAME;
			default -> throw new QueryParserException("Field '" + token.getValue() + "' is invalid.");
			};

			token = lexer.nextToken();
			if (token.getType() != QueryTokenType.OPERATOR)
				throw new QueryParserException("The second element of the expression must be an operator.");

			IComparisonOperator comparisonOperator = switch (token.getValue()) {
			case ">" -> ComparisonOperators.GREATER;
			case "<" -> ComparisonOperators.LESS;
			case "=" -> ComparisonOperators.EQUALS;
			case ">=" -> ComparisonOperators.GREATER_OR_EQUALS;
			case "<=" -> ComparisonOperators.LESS_OR_EQUALS;
			case "!=" -> ComparisonOperators.NOT_EQUALS;
			case "LIKE" -> ComparisonOperators.LIKE;
			default -> throw new QueryParserException("Operator '" + token.getValue() + "' is invalid.");
			};

			token = lexer.nextToken();
			if (token.getType() != QueryTokenType.VALUE)
				throw new QueryParserException("The third element of the expression must be a string.");

			String stringLiteral = token.getValue().substring(1, token.getValue().length() - 1);

			ConditionalExpression ex = new ConditionalExpression(fieldGetter, comparisonOperator, stringLiteral);
			expressions.add(ex);

			token = lexer.nextToken();
			boolean end = false;
			switch (token.getType()) {
			case EOF -> end = true;
			case AND -> token = lexer.nextToken();
			default -> throw new QueryParserException("This query is invalid");
			}
			if (end)
				break;
		}

	}

}
