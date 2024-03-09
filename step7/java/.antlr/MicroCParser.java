// Generated from /home/shay/a/ding276/Desktop/ECE468_573/fa2023-468-step6-haoyu-ding-sbljz/java/MicroC.g4 by ANTLR 4.13.1


package compiler;

import java.util.List;
import java.util.LinkedList;
import ast.*;
import compiler.Scope.*;


import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class MicroCParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, IDENTIFIER=33, INT_LITERAL=34, FLOAT_LITERAL=35, STR_LITERAL=36, 
		COMMENT=37, WS=38;
	public static final int
		RULE_program = 0, RULE_decls = 1, RULE_var_decls = 2, RULE_id = 3, RULE_var_decl = 4, 
		RULE_str_decl = 5, RULE_type = 6, RULE_base_type = 7, RULE_func_type = 8, 
		RULE_func_decl = 9, RULE_functions = 10, RULE_function = 11, RULE_params = 12, 
		RULE_params_rest = 13, RULE_param = 14, RULE_statements = 15, RULE_statement = 16, 
		RULE_base_stmt = 17, RULE_read_stmt = 18, RULE_print_stmt = 19, RULE_return_stmt = 20, 
		RULE_assign_stmt = 21, RULE_lhs = 22, RULE_if_stmt = 23, RULE_else_stmt = 24, 
		RULE_while_stmt = 25, RULE_lval = 26, RULE_primary = 27, RULE_unaryminus_expr = 28, 
		RULE_ptr_expr = 29, RULE_addr_of_expr = 30, RULE_array_expr = 31, RULE_call_expr = 32, 
		RULE_arg_list = 33, RULE_args_rest = 34, RULE_expr = 35, RULE_term = 36, 
		RULE_cond = 37, RULE_cmpop = 38, RULE_mulop = 39, RULE_addop = 40;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "decls", "var_decls", "id", "var_decl", "str_decl", "type", 
			"base_type", "func_type", "func_decl", "functions", "function", "params", 
			"params_rest", "param", "statements", "statement", "base_stmt", "read_stmt", 
			"print_stmt", "return_stmt", "assign_stmt", "lhs", "if_stmt", "else_stmt", 
			"while_stmt", "lval", "primary", "unaryminus_expr", "ptr_expr", "addr_of_expr", 
			"array_expr", "call_expr", "arg_list", "args_rest", "expr", "term", "cond", 
			"cmpop", "mulop", "addop"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'string'", "'='", "'*'", "'int'", "'float'", "'void'", 
			"'('", "')'", "'{'", "'}'", "','", "'read'", "'print'", "'return'", "'if'", 
			"'else'", "'while'", "'-'", "'&'", "'['", "']'", "'malloc'", "'free'", 
			"'<'", "'<='", "'>='", "'=='", "'!='", "'>'", "'/'", "'+'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, "IDENTIFIER", "INT_LITERAL", 
			"FLOAT_LITERAL", "STR_LITERAL", "COMMENT", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "MicroC.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	     private SymbolTable st; //Symbol table for the program
	     private ASTNode ast; //AST for the program

	     public void setSymbolTable(SymbolTable st) {
	          this.st = st;
	     }

	     public SymbolTable getSymbolTable() {
	          return st;
	     }

	     public ASTNode getAST() {
	          return ast;
	     }

	     private void addParams(List<Scope.Type> types, List<String> names) {
	          /* Add parameters in reverse order so everything matches correctly */
	          for (int i = types.size() - 1; i >= 0; --i) {
	               st.addArgument(types.get(i), names.get(i));
	          }
	     }

	public MicroCParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public FunctionsContext functions;
		public DeclsContext decls() {
			return getRuleContext(DeclsContext.class,0);
		}
		public FunctionsContext functions() {
			return getRuleContext(FunctionsContext.class,0);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			decls();
			setState(83);
			((ProgramContext)_localctx).functions = functions();
			ast = ((ProgramContext)_localctx).functions.node;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class DeclsContext extends ParserRuleContext {
		public Var_declContext var_decl() {
			return getRuleContext(Var_declContext.class,0);
		}
		public DeclsContext decls() {
			return getRuleContext(DeclsContext.class,0);
		}
		public Str_declContext str_decl() {
			return getRuleContext(Str_declContext.class,0);
		}
		public Func_declContext func_decl() {
			return getRuleContext(Func_declContext.class,0);
		}
		public DeclsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decls; }
	}

	public final DeclsContext decls() throws RecognitionException {
		DeclsContext _localctx = new DeclsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_decls);
		try {
			setState(96);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(86);
				var_decl();
				setState(87);
				decls();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(89);
				str_decl();
				setState(90);
				decls();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(92);
				func_decl();
				setState(93);
				decls();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Var_declsContext extends ParserRuleContext {
		public Var_declContext var_decl() {
			return getRuleContext(Var_declContext.class,0);
		}
		public Var_declsContext var_decls() {
			return getRuleContext(Var_declsContext.class,0);
		}
		public Var_declsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_decls; }
	}

	public final Var_declsContext var_decls() throws RecognitionException {
		Var_declsContext _localctx = new Var_declsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_var_decls);
		try {
			setState(102);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(98);
				var_decl();
				setState(99);
				var_decls();
				}
				break;
			case T__3:
			case T__10:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__17:
			case T__22:
			case T__23:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IdContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(MicroCParser.IDENTIFIER, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_id);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Var_declContext extends ParserRuleContext {
		public TypeContext type;
		public IdContext id;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public Var_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_decl; }
	}

	public final Var_declContext var_decl() throws RecognitionException {
		Var_declContext _localctx = new Var_declContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_var_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			((Var_declContext)_localctx).type = type(0);
			setState(107);
			((Var_declContext)_localctx).id = id();
			setState(108);
			match(T__0);
			st.addVariable(((Var_declContext)_localctx).type.t, (((Var_declContext)_localctx).id!=null?_input.getText(((Var_declContext)_localctx).id.start,((Var_declContext)_localctx).id.stop):null));
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Str_declContext extends ParserRuleContext {
		public IdContext id;
		public Token val;
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode STR_LITERAL() { return getToken(MicroCParser.STR_LITERAL, 0); }
		public Str_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_str_decl; }
	}

	public final Str_declContext str_decl() throws RecognitionException {
		Str_declContext _localctx = new Str_declContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_str_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			match(T__1);
			setState(112);
			((Str_declContext)_localctx).id = id();
			setState(113);
			match(T__2);
			setState(114);
			((Str_declContext)_localctx).val = match(STR_LITERAL);
			setState(115);
			match(T__0);
			st.addVariable(new Scope.Type(Scope.InnerType.STRING), (((Str_declContext)_localctx).id!=null?_input.getText(((Str_declContext)_localctx).id.start,((Str_declContext)_localctx).id.stop):null), (((Str_declContext)_localctx).val!=null?((Str_declContext)_localctx).val.getText():null));
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public Scope.Type t;
		public TypeContext t1;
		public Base_typeContext base_type;
		public Base_typeContext base_type() {
			return getRuleContext(Base_typeContext.class,0);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		return type(0);
	}

	private TypeContext type(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TypeContext _localctx = new TypeContext(_ctx, _parentState);
		TypeContext _prevctx = _localctx;
		int _startState = 12;
		enterRecursionRule(_localctx, 12, RULE_type, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(119);
			((TypeContext)_localctx).base_type = base_type();
			((TypeContext)_localctx).t =  ((TypeContext)_localctx).base_type.t;
			}
			_ctx.stop = _input.LT(-1);
			setState(127);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TypeContext(_parentctx, _parentState);
					_localctx.t1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_type);
					setState(122);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(123);
					match(T__3);
					((TypeContext)_localctx).t =  Scope.Type.pointerToType(((TypeContext)_localctx).t1.t);
					}
					} 
				}
				setState(129);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Base_typeContext extends ParserRuleContext {
		public Scope.Type t;
		public Base_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_base_type; }
	}

	public final Base_typeContext base_type() throws RecognitionException {
		Base_typeContext _localctx = new Base_typeContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_base_type);
		try {
			setState(134);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(130);
				match(T__4);
				((Base_typeContext)_localctx).t =  new Scope.Type(Scope.InnerType.INT);
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(132);
				match(T__5);
				((Base_typeContext)_localctx).t =  new Scope.Type(Scope.InnerType.FLOAT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Func_typeContext extends ParserRuleContext {
		public Scope.Type t;
		public TypeContext type;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public Func_typeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_type; }
	}

	public final Func_typeContext func_type() throws RecognitionException {
		Func_typeContext _localctx = new Func_typeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_func_type);
		try {
			setState(141);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(136);
				((Func_typeContext)_localctx).type = type(0);
				((Func_typeContext)_localctx).t =  ((Func_typeContext)_localctx).type.t;
				}
				break;
			case T__6:
				enterOuterAlt(_localctx, 2);
				{
				setState(139);
				match(T__6);
				((Func_typeContext)_localctx).t =  new Scope.Type(Scope.InnerType.VOID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Func_declContext extends ParserRuleContext {
		public Func_typeContext func_type;
		public IdContext id;
		public ParamsContext params;
		public Func_typeContext func_type() {
			return getRuleContext(Func_typeContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public Func_declContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_func_decl; }
	}

	public final Func_declContext func_decl() throws RecognitionException {
		Func_declContext _localctx = new Func_declContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_func_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(143);
			((Func_declContext)_localctx).func_type = func_type();
			setState(144);
			((Func_declContext)_localctx).id = id();
			setState(145);
			match(T__7);
			setState(146);
			((Func_declContext)_localctx).params = params();
			setState(147);
			match(T__8);
			setState(148);
			match(T__0);
			st.addFunction(((Func_declContext)_localctx).func_type.t, (((Func_declContext)_localctx).id!=null?_input.getText(((Func_declContext)_localctx).id.start,((Func_declContext)_localctx).id.stop):null), ((Func_declContext)_localctx).params.types);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionsContext extends ParserRuleContext {
		public FunctionListNode node;
		public FunctionContext function;
		public FunctionsContext functions;
		public FunctionContext function() {
			return getRuleContext(FunctionContext.class,0);
		}
		public FunctionsContext functions() {
			return getRuleContext(FunctionsContext.class,0);
		}
		public FunctionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functions; }
	}

	public final FunctionsContext functions() throws RecognitionException {
		FunctionsContext _localctx = new FunctionsContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_functions);
		try {
			setState(156);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
			case T__5:
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(151);
				((FunctionsContext)_localctx).function = function();
				setState(152);
				((FunctionsContext)_localctx).functions = functions();
				((FunctionsContext)_localctx).node =  new FunctionListNode(((FunctionsContext)_localctx).function.node, ((FunctionsContext)_localctx).functions.node);
				}
				break;
			case EOF:
				enterOuterAlt(_localctx, 2);
				{
				((FunctionsContext)_localctx).node =  new FunctionListNode();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunctionContext extends ParserRuleContext {
		public FunctionNode node;
		public Func_typeContext func_type;
		public IdContext id;
		public ParamsContext params;
		public StatementsContext statements;
		public Func_typeContext func_type() {
			return getRuleContext(Func_typeContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ParamsContext params() {
			return getRuleContext(ParamsContext.class,0);
		}
		public Var_declsContext var_decls() {
			return getRuleContext(Var_declsContext.class,0);
		}
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_function);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			((FunctionContext)_localctx).func_type = func_type();
			setState(159);
			((FunctionContext)_localctx).id = id();
			setState(160);
			match(T__7);
			setState(161);
			((FunctionContext)_localctx).params = params();
			setState(162);
			match(T__8);

			           /* Add FunctionSymbolTable entry to global scope */
			          FunctionSymbolTableEntry ste = (FunctionSymbolTableEntry) st.getSymbolTableEntry((((FunctionContext)_localctx).id!=null?_input.getText(((FunctionContext)_localctx).id.start,((FunctionContext)_localctx).id.stop):null));
			          if ((ste == null) || !ste.isDefined()) {
			               st.addFunction(((FunctionContext)_localctx).func_type.t, (((FunctionContext)_localctx).id!=null?_input.getText(((FunctionContext)_localctx).id.start,((FunctionContext)_localctx).id.stop):null), ((FunctionContext)_localctx).params.types);          
			               ste = (FunctionSymbolTableEntry) st.getSymbolTableEntry((((FunctionContext)_localctx).id!=null?_input.getText(((FunctionContext)_localctx).id.start,((FunctionContext)_localctx).id.stop):null));
			               ste.setDefined(true);
			          } else {
			               throw new Error("Function already defined");
			          }
			           st.pushScope((((FunctionContext)_localctx).id!=null?_input.getText(((FunctionContext)_localctx).id.start,((FunctionContext)_localctx).id.stop):null));
			           addParams(((FunctionContext)_localctx).params.types, ((FunctionContext)_localctx).params.names);
			      
			setState(164);
			match(T__9);
			setState(165);
			var_decls();
			setState(166);
			((FunctionContext)_localctx).statements = statements();
			setState(167);
			match(T__10);

			          /* Create FunctionNode */
			          LocalScope funcScope = (LocalScope) st.currentScope();
			          ((FunctionContext)_localctx).node =  new FunctionNode(((FunctionContext)_localctx).statements.node, (((FunctionContext)_localctx).id!=null?_input.getText(((FunctionContext)_localctx).id.start,((FunctionContext)_localctx).id.stop):null), funcScope);

			          /* Done with this scope, so pop the scope */
			          st.popScope();
			     
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamsContext extends ParserRuleContext {
		public LinkedList<String> names;
		public LinkedList<Scope.Type> types;
		public ParamContext param;
		public Params_restContext params_rest;
		public ParamContext param() {
			return getRuleContext(ParamContext.class,0);
		}
		public Params_restContext params_rest() {
			return getRuleContext(Params_restContext.class,0);
		}
		public ParamsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params; }
	}

	public final ParamsContext params() throws RecognitionException {
		ParamsContext _localctx = new ParamsContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_params);
		try {
			setState(175);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
			case T__5:
				enterOuterAlt(_localctx, 1);
				{
				setState(170);
				((ParamsContext)_localctx).param = param();
				setState(171);
				((ParamsContext)_localctx).params_rest = params_rest();

				               ((ParamsContext)_localctx).names =  new LinkedList<String>();
				               ((ParamsContext)_localctx).types =  new LinkedList<Scope.Type>();
				               _localctx.names.add(((ParamsContext)_localctx).param.name); _localctx.names.addAll(((ParamsContext)_localctx).params_rest.names);
				               _localctx.types.add(((ParamsContext)_localctx).param.param_type); _localctx.types.addAll(((ParamsContext)_localctx).params_rest.types);
				          
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				((ParamsContext)_localctx).names =  new LinkedList<String>(); ((ParamsContext)_localctx).types =  new LinkedList<Scope.Type>();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Params_restContext extends ParserRuleContext {
		public LinkedList<String> names;
		public LinkedList<Scope.Type> types;
		public ParamContext param;
		public Params_restContext params_rest;
		public ParamContext param() {
			return getRuleContext(ParamContext.class,0);
		}
		public Params_restContext params_rest() {
			return getRuleContext(Params_restContext.class,0);
		}
		public Params_restContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_params_rest; }
	}

	public final Params_restContext params_rest() throws RecognitionException {
		Params_restContext _localctx = new Params_restContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_params_rest);
		try {
			setState(183);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
				enterOuterAlt(_localctx, 1);
				{
				setState(177);
				match(T__11);
				setState(178);
				((Params_restContext)_localctx).param = param();
				setState(179);
				((Params_restContext)_localctx).params_rest = params_rest();

				               ((Params_restContext)_localctx).names =  new LinkedList<String>();
				               ((Params_restContext)_localctx).types =  new LinkedList<Scope.Type>();
				               _localctx.names.add(((Params_restContext)_localctx).param.name); _localctx.names.addAll(((Params_restContext)_localctx).params_rest.names);
				               _localctx.types.add(((Params_restContext)_localctx).param.param_type); _localctx.types.addAll(((Params_restContext)_localctx).params_rest.types);
				          
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				((Params_restContext)_localctx).names =  new LinkedList<String>(); ((Params_restContext)_localctx).types =  new LinkedList<Scope.Type>();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParamContext extends ParserRuleContext {
		public String name;
		public Scope.Type param_type;
		public TypeContext type;
		public IdContext id;
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ParamContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_param; }
	}

	public final ParamContext param() throws RecognitionException {
		ParamContext _localctx = new ParamContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_param);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			((ParamContext)_localctx).type = type(0);
			setState(186);
			((ParamContext)_localctx).id = id();
			((ParamContext)_localctx).name =  (((ParamContext)_localctx).id!=null?_input.getText(((ParamContext)_localctx).id.start,((ParamContext)_localctx).id.stop):null); ((ParamContext)_localctx).param_type =  ((ParamContext)_localctx).type.t;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementsContext extends ParserRuleContext {
		public StatementListNode node;
		public StatementContext statement;
		public StatementsContext s;
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public StatementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statements; }
	}

	public final StatementsContext statements() throws RecognitionException {
		StatementsContext _localctx = new StatementsContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_statements);
		try {
			setState(194);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__17:
			case T__22:
			case T__23:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(189);
				((StatementsContext)_localctx).statement = statement();
				setState(190);
				((StatementsContext)_localctx).s = statements();
				((StatementsContext)_localctx).node =  new StatementListNode(((StatementsContext)_localctx).statement.node, ((StatementsContext)_localctx).s.node);
				}
				break;
			case T__10:
				enterOuterAlt(_localctx, 2);
				{
				((StatementsContext)_localctx).node =  new StatementListNode();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public StatementNode node;
		public Base_stmtContext base_stmt;
		public If_stmtContext if_stmt;
		public While_stmtContext while_stmt;
		public Base_stmtContext base_stmt() {
			return getRuleContext(Base_stmtContext.class,0);
		}
		public If_stmtContext if_stmt() {
			return getRuleContext(If_stmtContext.class,0);
		}
		public While_stmtContext while_stmt() {
			return getRuleContext(While_stmtContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_statement);
		try {
			setState(206);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
			case T__12:
			case T__13:
			case T__14:
			case T__22:
			case T__23:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(196);
				((StatementContext)_localctx).base_stmt = base_stmt();
				setState(197);
				match(T__0);
				((StatementContext)_localctx).node =  ((StatementContext)_localctx).base_stmt.node;
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 2);
				{
				setState(200);
				((StatementContext)_localctx).if_stmt = if_stmt();
				((StatementContext)_localctx).node =  ((StatementContext)_localctx).if_stmt.node;
				}
				break;
			case T__17:
				enterOuterAlt(_localctx, 3);
				{
				setState(203);
				((StatementContext)_localctx).while_stmt = while_stmt();
				((StatementContext)_localctx).node =  ((StatementContext)_localctx).while_stmt.node;
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Base_stmtContext extends ParserRuleContext {
		public StatementNode node;
		public Assign_stmtContext assign_stmt;
		public Read_stmtContext read_stmt;
		public Print_stmtContext print_stmt;
		public Return_stmtContext return_stmt;
		public Call_exprContext call_expr;
		public Assign_stmtContext assign_stmt() {
			return getRuleContext(Assign_stmtContext.class,0);
		}
		public Read_stmtContext read_stmt() {
			return getRuleContext(Read_stmtContext.class,0);
		}
		public Print_stmtContext print_stmt() {
			return getRuleContext(Print_stmtContext.class,0);
		}
		public Return_stmtContext return_stmt() {
			return getRuleContext(Return_stmtContext.class,0);
		}
		public Call_exprContext call_expr() {
			return getRuleContext(Call_exprContext.class,0);
		}
		public Base_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_base_stmt; }
	}

	public final Base_stmtContext base_stmt() throws RecognitionException {
		Base_stmtContext _localctx = new Base_stmtContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_base_stmt);
		try {
			setState(223);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,10,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(208);
				((Base_stmtContext)_localctx).assign_stmt = assign_stmt();
				((Base_stmtContext)_localctx).node =  ((Base_stmtContext)_localctx).assign_stmt.node;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(211);
				((Base_stmtContext)_localctx).read_stmt = read_stmt();
				((Base_stmtContext)_localctx).node =  ((Base_stmtContext)_localctx).read_stmt.node;
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(214);
				((Base_stmtContext)_localctx).print_stmt = print_stmt();
				((Base_stmtContext)_localctx).node =  ((Base_stmtContext)_localctx).print_stmt.node;
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(217);
				((Base_stmtContext)_localctx).return_stmt = return_stmt();
				((Base_stmtContext)_localctx).node =  ((Base_stmtContext)_localctx).return_stmt.node;
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(220);
				((Base_stmtContext)_localctx).call_expr = call_expr();
				((Base_stmtContext)_localctx).node =  ((Base_stmtContext)_localctx).call_expr.node;
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Read_stmtContext extends ParserRuleContext {
		public ReadNode node;
		public IdContext id;
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public Read_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_read_stmt; }
	}

	public final Read_stmtContext read_stmt() throws RecognitionException {
		Read_stmtContext _localctx = new Read_stmtContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_read_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			match(T__12);
			setState(226);
			match(T__7);
			setState(227);
			((Read_stmtContext)_localctx).id = id();
			setState(228);
			match(T__8);
			((Read_stmtContext)_localctx).node =  new ReadNode(new VarNode((((Read_stmtContext)_localctx).id!=null?_input.getText(((Read_stmtContext)_localctx).id.start,((Read_stmtContext)_localctx).id.stop):null)));
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Print_stmtContext extends ParserRuleContext {
		public WriteNode node;
		public ExprContext expr;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Print_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_print_stmt; }
	}

	public final Print_stmtContext print_stmt() throws RecognitionException {
		Print_stmtContext _localctx = new Print_stmtContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_print_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			match(T__13);
			setState(232);
			match(T__7);
			setState(233);
			((Print_stmtContext)_localctx).expr = expr(0);
			setState(234);
			match(T__8);
			((Print_stmtContext)_localctx).node =  new WriteNode(((Print_stmtContext)_localctx).expr.node);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Return_stmtContext extends ParserRuleContext {
		public ReturnNode node;
		public ExprContext expr;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Return_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_return_stmt; }
	}

	public final Return_stmtContext return_stmt() throws RecognitionException {
		Return_stmtContext _localctx = new Return_stmtContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_return_stmt);
		try {
			setState(243);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(237);
				match(T__14);
				setState(238);
				((Return_stmtContext)_localctx).expr = expr(0);
				((Return_stmtContext)_localctx).node =  new ReturnNode(((Return_stmtContext)_localctx).expr.node, st.getFunctionSymbol(st.currentScope().getName()));
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(241);
				match(T__14);
				((Return_stmtContext)_localctx).node =  new ReturnNode(null, st.getFunctionSymbol(st.currentScope().getName()));
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Assign_stmtContext extends ParserRuleContext {
		public AssignNode node;
		public LhsContext lhs;
		public ExprContext expr;
		public LhsContext lhs() {
			return getRuleContext(LhsContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Assign_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assign_stmt; }
	}

	public final Assign_stmtContext assign_stmt() throws RecognitionException {
		Assign_stmtContext _localctx = new Assign_stmtContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_assign_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(245);
			((Assign_stmtContext)_localctx).lhs = lhs();
			setState(246);
			match(T__2);
			setState(247);
			((Assign_stmtContext)_localctx).expr = expr(0);
			((Assign_stmtContext)_localctx).node =  new AssignNode(((Assign_stmtContext)_localctx).lhs.node, ((Assign_stmtContext)_localctx).expr.node);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LhsContext extends ParserRuleContext {
		public ExpressionNode node;
		public LvalContext lval;
		public Array_exprContext array_expr;
		public LvalContext lval() {
			return getRuleContext(LvalContext.class,0);
		}
		public Array_exprContext array_expr() {
			return getRuleContext(Array_exprContext.class,0);
		}
		public LhsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lhs; }
	}

	public final LhsContext lhs() throws RecognitionException {
		LhsContext _localctx = new LhsContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_lhs);
		try {
			setState(256);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(250);
				((LhsContext)_localctx).lval = lval();
				((LhsContext)_localctx).node =  ((LhsContext)_localctx).lval.node;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(253);
				((LhsContext)_localctx).array_expr = array_expr(0);
				((LhsContext)_localctx).node =  ((LhsContext)_localctx).array_expr.node;
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class If_stmtContext extends ParserRuleContext {
		public IfStatementNode node;
		public CondContext cond;
		public StatementsContext statements;
		public Else_stmtContext else_stmt;
		public CondContext cond() {
			return getRuleContext(CondContext.class,0);
		}
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public Else_stmtContext else_stmt() {
			return getRuleContext(Else_stmtContext.class,0);
		}
		public If_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_if_stmt; }
	}

	public final If_stmtContext if_stmt() throws RecognitionException {
		If_stmtContext _localctx = new If_stmtContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_if_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(258);
			match(T__15);
			setState(259);
			match(T__7);
			setState(260);
			((If_stmtContext)_localctx).cond = cond();
			setState(261);
			match(T__8);
			setState(262);
			match(T__9);
			setState(263);
			((If_stmtContext)_localctx).statements = statements();
			setState(264);
			match(T__10);
			setState(265);
			((If_stmtContext)_localctx).else_stmt = else_stmt();
			((If_stmtContext)_localctx).node =  new IfStatementNode(((If_stmtContext)_localctx).cond.node,((If_stmtContext)_localctx).statements.node,((If_stmtContext)_localctx).else_stmt.node);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Else_stmtContext extends ParserRuleContext {
		public StatementListNode node;
		public StatementsContext statements;
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public Else_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_else_stmt; }
	}

	public final Else_stmtContext else_stmt() throws RecognitionException {
		Else_stmtContext _localctx = new Else_stmtContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_else_stmt);
		try {
			setState(275);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__16:
				enterOuterAlt(_localctx, 1);
				{
				setState(268);
				match(T__16);
				setState(269);
				match(T__9);
				setState(270);
				((Else_stmtContext)_localctx).statements = statements();
				setState(271);
				match(T__10);
				((Else_stmtContext)_localctx).node =  ((Else_stmtContext)_localctx).statements.node;
				}
				break;
			case T__3:
			case T__10:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__17:
			case T__22:
			case T__23:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				((Else_stmtContext)_localctx).node =  new StatementListNode();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class While_stmtContext extends ParserRuleContext {
		public WhileNode node;
		public CondContext cond;
		public StatementsContext statements;
		public CondContext cond() {
			return getRuleContext(CondContext.class,0);
		}
		public StatementsContext statements() {
			return getRuleContext(StatementsContext.class,0);
		}
		public While_stmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_while_stmt; }
	}

	public final While_stmtContext while_stmt() throws RecognitionException {
		While_stmtContext _localctx = new While_stmtContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_while_stmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(277);
			match(T__17);
			setState(278);
			match(T__7);
			setState(279);
			((While_stmtContext)_localctx).cond = cond();
			setState(280);
			match(T__8);
			setState(281);
			match(T__9);
			setState(282);
			((While_stmtContext)_localctx).statements = statements();
			setState(283);
			match(T__10);
			((While_stmtContext)_localctx).node =  new WhileNode(((While_stmtContext)_localctx).cond.node,((While_stmtContext)_localctx).statements.node);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LvalContext extends ParserRuleContext {
		public ExpressionNode node;
		public IdContext id;
		public Ptr_exprContext ptr_expr;
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public Ptr_exprContext ptr_expr() {
			return getRuleContext(Ptr_exprContext.class,0);
		}
		public LvalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lval; }
	}

	public final LvalContext lval() throws RecognitionException {
		LvalContext _localctx = new LvalContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_lval);
		try {
			setState(292);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				enterOuterAlt(_localctx, 1);
				{
				setState(286);
				((LvalContext)_localctx).id = id();
				((LvalContext)_localctx).node =  new VarNode((((LvalContext)_localctx).id!=null?_input.getText(((LvalContext)_localctx).id.start,((LvalContext)_localctx).id.stop):null));
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(289);
				((LvalContext)_localctx).ptr_expr = ptr_expr();
				((LvalContext)_localctx).node =  ((LvalContext)_localctx).ptr_expr.node;
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryContext extends ParserRuleContext {
		public ExpressionNode node;
		public LvalContext lval;
		public Addr_of_exprContext addr_of_expr;
		public ExprContext expr;
		public Unaryminus_exprContext unaryminus_expr;
		public Call_exprContext call_expr;
		public Array_exprContext array_expr;
		public Token il;
		public Token fl;
		public LvalContext lval() {
			return getRuleContext(LvalContext.class,0);
		}
		public Addr_of_exprContext addr_of_expr() {
			return getRuleContext(Addr_of_exprContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Unaryminus_exprContext unaryminus_expr() {
			return getRuleContext(Unaryminus_exprContext.class,0);
		}
		public Call_exprContext call_expr() {
			return getRuleContext(Call_exprContext.class,0);
		}
		public Array_exprContext array_expr() {
			return getRuleContext(Array_exprContext.class,0);
		}
		public TerminalNode INT_LITERAL() { return getToken(MicroCParser.INT_LITERAL, 0); }
		public TerminalNode FLOAT_LITERAL() { return getToken(MicroCParser.FLOAT_LITERAL, 0); }
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_primary);
		try {
			setState(318);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(294);
				((PrimaryContext)_localctx).lval = lval();
				((PrimaryContext)_localctx).node =  ((PrimaryContext)_localctx).lval.node;
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(297);
				((PrimaryContext)_localctx).addr_of_expr = addr_of_expr();
				((PrimaryContext)_localctx).node =  ((PrimaryContext)_localctx).addr_of_expr.node;
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(300);
				match(T__7);
				setState(301);
				((PrimaryContext)_localctx).expr = expr(0);
				setState(302);
				match(T__8);
				((PrimaryContext)_localctx).node =  ((PrimaryContext)_localctx).expr.node;
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(305);
				((PrimaryContext)_localctx).unaryminus_expr = unaryminus_expr();
				((PrimaryContext)_localctx).node =  ((PrimaryContext)_localctx).unaryminus_expr.node;
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(308);
				((PrimaryContext)_localctx).call_expr = call_expr();
				((PrimaryContext)_localctx).node =  ((PrimaryContext)_localctx).call_expr.node;
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(311);
				((PrimaryContext)_localctx).array_expr = array_expr(0);
				((PrimaryContext)_localctx).node =  ((PrimaryContext)_localctx).array_expr.node;
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(314);
				((PrimaryContext)_localctx).il = match(INT_LITERAL);
				((PrimaryContext)_localctx).node =  new IntLitNode((((PrimaryContext)_localctx).il!=null?((PrimaryContext)_localctx).il.getText():null));
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(316);
				((PrimaryContext)_localctx).fl = match(FLOAT_LITERAL);
				((PrimaryContext)_localctx).node =  new FloatLitNode((((PrimaryContext)_localctx).fl!=null?((PrimaryContext)_localctx).fl.getText():null));
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Unaryminus_exprContext extends ParserRuleContext {
		public ExpressionNode node;
		public ExprContext expr;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Unaryminus_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryminus_expr; }
	}

	public final Unaryminus_exprContext unaryminus_expr() throws RecognitionException {
		Unaryminus_exprContext _localctx = new Unaryminus_exprContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_unaryminus_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(320);
			match(T__18);
			setState(321);
			((Unaryminus_exprContext)_localctx).expr = expr(0);
			((Unaryminus_exprContext)_localctx).node =  new UnaryOpNode(((Unaryminus_exprContext)_localctx).expr.node,"-");
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Ptr_exprContext extends ParserRuleContext {
		public PtrDerefNode node;
		public PrimaryContext primary;
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public Ptr_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ptr_expr; }
	}

	public final Ptr_exprContext ptr_expr() throws RecognitionException {
		Ptr_exprContext _localctx = new Ptr_exprContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_ptr_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			match(T__3);
			setState(325);
			((Ptr_exprContext)_localctx).primary = primary();
			((Ptr_exprContext)_localctx).node =  new PtrDerefNode(((Ptr_exprContext)_localctx).primary.node);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Addr_of_exprContext extends ParserRuleContext {
		public AddrOfNode node;
		public LvalContext lval;
		public Array_exprContext array_expr;
		public LvalContext lval() {
			return getRuleContext(LvalContext.class,0);
		}
		public Array_exprContext array_expr() {
			return getRuleContext(Array_exprContext.class,0);
		}
		public Addr_of_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_addr_of_expr; }
	}

	public final Addr_of_exprContext addr_of_expr() throws RecognitionException {
		Addr_of_exprContext _localctx = new Addr_of_exprContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_addr_of_expr);
		try {
			setState(336);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(328);
				match(T__19);
				setState(329);
				((Addr_of_exprContext)_localctx).lval = lval();
				((Addr_of_exprContext)_localctx).node =  new AddrOfNode(((Addr_of_exprContext)_localctx).lval.node);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(332);
				match(T__19);
				setState(333);
				((Addr_of_exprContext)_localctx).array_expr = array_expr(0);
				((Addr_of_exprContext)_localctx).node =  new AddrOfNode(((Addr_of_exprContext)_localctx).array_expr.node);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Array_exprContext extends ParserRuleContext {
		public PtrDerefNode node;
		public Array_exprContext ae;
		public LvalContext lval;
		public ExprContext expr;
		public LvalContext lval() {
			return getRuleContext(LvalContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Array_exprContext array_expr() {
			return getRuleContext(Array_exprContext.class,0);
		}
		public Array_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_array_expr; }
	}

	public final Array_exprContext array_expr() throws RecognitionException {
		return array_expr(0);
	}

	private Array_exprContext array_expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Array_exprContext _localctx = new Array_exprContext(_ctx, _parentState);
		Array_exprContext _prevctx = _localctx;
		int _startState = 62;
		enterRecursionRule(_localctx, 62, RULE_array_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(339);
			((Array_exprContext)_localctx).lval = lval();
			setState(340);
			match(T__20);
			setState(341);
			((Array_exprContext)_localctx).expr = expr(0);
			setState(342);
			match(T__21);
			((Array_exprContext)_localctx).node = new PtrDerefNode(new BinaryOpNode(((Array_exprContext)_localctx).lval.node, new BinaryOpNode(((Array_exprContext)_localctx).expr.node, new IntLitNode("4"), "*"), "+"));
			}
			_ctx.stop = _input.LT(-1);
			setState(353);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Array_exprContext(_parentctx, _parentState);
					_localctx.ae = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_array_expr);
					setState(345);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(346);
					match(T__20);
					setState(347);
					((Array_exprContext)_localctx).expr = expr(0);
					setState(348);
					match(T__21);
					((Array_exprContext)_localctx).node =  new PtrDerefNode(new BinaryOpNode(((Array_exprContext)_localctx).ae.node,new BinaryOpNode(((Array_exprContext)_localctx).expr.node,new IntLitNode("4"),"*"),"+"));
					}
					} 
				}
				setState(355);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,17,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Call_exprContext extends ParserRuleContext {
		public AbstractCallNode node;
		public ExprContext expr;
		public IdContext id;
		public Arg_listContext arg_list;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public Arg_listContext arg_list() {
			return getRuleContext(Arg_listContext.class,0);
		}
		public Call_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_call_expr; }
	}

	public final Call_exprContext call_expr() throws RecognitionException {
		Call_exprContext _localctx = new Call_exprContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_call_expr);
		try {
			setState(374);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__22:
				enterOuterAlt(_localctx, 1);
				{
				setState(356);
				match(T__22);
				setState(357);
				match(T__7);
				setState(358);
				((Call_exprContext)_localctx).expr = expr(0);
				setState(359);
				match(T__8);
				((Call_exprContext)_localctx).node =  new MallocNode(((Call_exprContext)_localctx).expr.node);
				}
				break;
			case T__23:
				enterOuterAlt(_localctx, 2);
				{
				setState(362);
				match(T__23);
				setState(363);
				match(T__7);
				setState(364);
				((Call_exprContext)_localctx).expr = expr(0);
				setState(365);
				match(T__8);
				((Call_exprContext)_localctx).node =  new FreeNode(((Call_exprContext)_localctx).expr.node);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 3);
				{
				setState(368);
				((Call_exprContext)_localctx).id = id();
				setState(369);
				match(T__7);
				setState(370);
				((Call_exprContext)_localctx).arg_list = arg_list();
				setState(371);
				match(T__8);
				((Call_exprContext)_localctx).node =  new CallNode((((Call_exprContext)_localctx).id!=null?_input.getText(((Call_exprContext)_localctx).id.start,((Call_exprContext)_localctx).id.stop):null), ((Call_exprContext)_localctx).arg_list.args);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Arg_listContext extends ParserRuleContext {
		public List<ExpressionNode> args;
		public ExprContext expr;
		public Args_restContext args_rest;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Args_restContext args_rest() {
			return getRuleContext(Args_restContext.class,0);
		}
		public Arg_listContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arg_list; }
	}

	public final Arg_listContext arg_list() throws RecognitionException {
		Arg_listContext _localctx = new Arg_listContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_arg_list);
		try {
			setState(381);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
			case T__7:
			case T__18:
			case T__19:
			case T__22:
			case T__23:
			case IDENTIFIER:
			case INT_LITERAL:
			case FLOAT_LITERAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(376);
				((Arg_listContext)_localctx).expr = expr(0);
				setState(377);
				((Arg_listContext)_localctx).args_rest = args_rest();
				((Arg_listContext)_localctx).args =  new LinkedList<ExpressionNode>(); _localctx.args.add(((Arg_listContext)_localctx).expr.node); _localctx.args.addAll(((Arg_listContext)_localctx).args_rest.args);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				((Arg_listContext)_localctx).args =  new LinkedList<ExpressionNode>();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class Args_restContext extends ParserRuleContext {
		public List<ExpressionNode> args;
		public ExprContext expr;
		public Args_restContext args_rest;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public Args_restContext args_rest() {
			return getRuleContext(Args_restContext.class,0);
		}
		public Args_restContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_args_rest; }
	}

	public final Args_restContext args_rest() throws RecognitionException {
		Args_restContext _localctx = new Args_restContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_args_rest);
		try {
			setState(389);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
				enterOuterAlt(_localctx, 1);
				{
				setState(383);
				match(T__11);
				setState(384);
				((Args_restContext)_localctx).expr = expr(0);
				setState(385);
				((Args_restContext)_localctx).args_rest = args_rest();
				((Args_restContext)_localctx).args =  new LinkedList<ExpressionNode>(); _localctx.args.add(((Args_restContext)_localctx).expr.node); _localctx.args.addAll(((Args_restContext)_localctx).args_rest.args);
				}
				break;
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				((Args_restContext)_localctx).args =  new LinkedList<ExpressionNode>();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public ExpressionNode node;
		public ExprContext e1;
		public TermContext term;
		public AddopContext addop;
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public AddopContext addop() {
			return getRuleContext(AddopContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 70;
		enterRecursionRule(_localctx, 70, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(392);
			((ExprContext)_localctx).term = term(0);
			((ExprContext)_localctx).node =  ((ExprContext)_localctx).term.node;
			}
			_ctx.stop = _input.LT(-1);
			setState(402);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new ExprContext(_parentctx, _parentState);
					_localctx.e1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_expr);
					setState(395);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(396);
					((ExprContext)_localctx).addop = addop();
					setState(397);
					((ExprContext)_localctx).term = term(0);
					((ExprContext)_localctx).node =  new BinaryOpNode(((ExprContext)_localctx).e1.node,((ExprContext)_localctx).term.node,(((ExprContext)_localctx).addop!=null?_input.getText(((ExprContext)_localctx).addop.start,((ExprContext)_localctx).addop.stop):null));
					}
					} 
				}
				setState(404);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,21,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ParserRuleContext {
		public ExpressionNode node;
		public TermContext t1;
		public PrimaryContext primary;
		public MulopContext mulop;
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public MulopContext mulop() {
			return getRuleContext(MulopContext.class,0);
		}
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	}

	public final TermContext term() throws RecognitionException {
		return term(0);
	}

	private TermContext term(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		TermContext _localctx = new TermContext(_ctx, _parentState);
		TermContext _prevctx = _localctx;
		int _startState = 72;
		enterRecursionRule(_localctx, 72, RULE_term, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(406);
			((TermContext)_localctx).primary = primary();
			((TermContext)_localctx).node =  ((TermContext)_localctx).primary.node;
			}
			_ctx.stop = _input.LT(-1);
			setState(416);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new TermContext(_parentctx, _parentState);
					_localctx.t1 = _prevctx;
					pushNewRecursionContext(_localctx, _startState, RULE_term);
					setState(409);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(410);
					((TermContext)_localctx).mulop = mulop();
					setState(411);
					((TermContext)_localctx).primary = primary();
					((TermContext)_localctx).node =  new BinaryOpNode(((TermContext)_localctx).t1.node,((TermContext)_localctx).primary.node,(((TermContext)_localctx).mulop!=null?_input.getText(((TermContext)_localctx).mulop.start,((TermContext)_localctx).mulop.stop):null));
					}
					} 
				}
				setState(418);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CondContext extends ParserRuleContext {
		public CondNode node;
		public ExprContext e1;
		public CmpopContext cmpop;
		public ExprContext e2;
		public CmpopContext cmpop() {
			return getRuleContext(CmpopContext.class,0);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public CondContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cond; }
	}

	public final CondContext cond() throws RecognitionException {
		CondContext _localctx = new CondContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_cond);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(419);
			((CondContext)_localctx).e1 = expr(0);
			setState(420);
			((CondContext)_localctx).cmpop = cmpop();
			setState(421);
			((CondContext)_localctx).e2 = expr(0);
			((CondContext)_localctx).node =  new CondNode(((CondContext)_localctx).e1.node,((CondContext)_localctx).e2.node,(((CondContext)_localctx).cmpop!=null?_input.getText(((CondContext)_localctx).cmpop.start,((CondContext)_localctx).cmpop.stop):null));
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class CmpopContext extends ParserRuleContext {
		public CmpopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cmpop; }
	}

	public final CmpopContext cmpop() throws RecognitionException {
		CmpopContext _localctx = new CmpopContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_cmpop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(424);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 2113929216L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MulopContext extends ParserRuleContext {
		public MulopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mulop; }
	}

	public final MulopContext mulop() throws RecognitionException {
		MulopContext _localctx = new MulopContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_mulop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(426);
			_la = _input.LA(1);
			if ( !(_la==T__3 || _la==T__30) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AddopContext extends ParserRuleContext {
		public AddopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_addop; }
	}

	public final AddopContext addop() throws RecognitionException {
		AddopContext _localctx = new AddopContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_addop);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(428);
			_la = _input.LA(1);
			if ( !(_la==T__18 || _la==T__31) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 6:
			return type_sempred((TypeContext)_localctx, predIndex);
		case 31:
			return array_expr_sempred((Array_exprContext)_localctx, predIndex);
		case 35:
			return expr_sempred((ExprContext)_localctx, predIndex);
		case 36:
			return term_sempred((TermContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean type_sempred(TypeContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean array_expr_sempred(Array_exprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 1:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean term_sempred(TermContext _localctx, int predIndex) {
		switch (predIndex) {
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001&\u01af\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007\"\u0002"+
		"#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007\'\u0002"+
		"(\u0007(\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0003\u0001a\b\u0001\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0003\u0002g\b\u0002\u0001\u0003\u0001"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001"+
		"\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0005\u0006~\b\u0006\n\u0006\f\u0006\u0081\t\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0003\u0007\u0087\b\u0007"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0003\b\u008e\b\b\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\n\u0001\n\u0001\n\u0003\n\u009d\b\n\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0003\f\u00b0\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003"+
		"\r\u00b8\b\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f\u00c3\b\u000f"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u00cf\b\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u00e0\b\u0011\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014"+
		"\u00f4\b\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0003\u0016\u0101\b\u0016\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018"+
		"\u0001\u0018\u0003\u0018\u0114\b\u0018\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a"+
		"\u0003\u001a\u0125\b\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0003\u001b\u013f\b\u001b\u0001\u001c\u0001\u001c"+
		"\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0003\u001e\u0151\b\u001e\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0001\u001f\u0005\u001f"+
		"\u0160\b\u001f\n\u001f\f\u001f\u0163\t\u001f\u0001 \u0001 \u0001 \u0001"+
		" \u0001 \u0001 \u0001 \u0001 \u0001 \u0001 \u0001 \u0001 \u0001 \u0001"+
		" \u0001 \u0001 \u0001 \u0001 \u0003 \u0177\b \u0001!\u0001!\u0001!\u0001"+
		"!\u0001!\u0003!\u017e\b!\u0001\"\u0001\"\u0001\"\u0001\"\u0001\"\u0001"+
		"\"\u0003\"\u0186\b\"\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#\u0001#"+
		"\u0001#\u0001#\u0005#\u0191\b#\n#\f#\u0194\t#\u0001$\u0001$\u0001$\u0001"+
		"$\u0001$\u0001$\u0001$\u0001$\u0001$\u0005$\u019f\b$\n$\f$\u01a2\t$\u0001"+
		"%\u0001%\u0001%\u0001%\u0001%\u0001&\u0001&\u0001\'\u0001\'\u0001(\u0001"+
		"(\u0001(\u0000\u0004\f>FH)\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012"+
		"\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDFHJLNP\u0000\u0003"+
		"\u0001\u0000\u0019\u001e\u0002\u0000\u0004\u0004\u001f\u001f\u0002\u0000"+
		"\u0013\u0013  \u01a9\u0000R\u0001\u0000\u0000\u0000\u0002`\u0001\u0000"+
		"\u0000\u0000\u0004f\u0001\u0000\u0000\u0000\u0006h\u0001\u0000\u0000\u0000"+
		"\bj\u0001\u0000\u0000\u0000\no\u0001\u0000\u0000\u0000\fv\u0001\u0000"+
		"\u0000\u0000\u000e\u0086\u0001\u0000\u0000\u0000\u0010\u008d\u0001\u0000"+
		"\u0000\u0000\u0012\u008f\u0001\u0000\u0000\u0000\u0014\u009c\u0001\u0000"+
		"\u0000\u0000\u0016\u009e\u0001\u0000\u0000\u0000\u0018\u00af\u0001\u0000"+
		"\u0000\u0000\u001a\u00b7\u0001\u0000\u0000\u0000\u001c\u00b9\u0001\u0000"+
		"\u0000\u0000\u001e\u00c2\u0001\u0000\u0000\u0000 \u00ce\u0001\u0000\u0000"+
		"\u0000\"\u00df\u0001\u0000\u0000\u0000$\u00e1\u0001\u0000\u0000\u0000"+
		"&\u00e7\u0001\u0000\u0000\u0000(\u00f3\u0001\u0000\u0000\u0000*\u00f5"+
		"\u0001\u0000\u0000\u0000,\u0100\u0001\u0000\u0000\u0000.\u0102\u0001\u0000"+
		"\u0000\u00000\u0113\u0001\u0000\u0000\u00002\u0115\u0001\u0000\u0000\u0000"+
		"4\u0124\u0001\u0000\u0000\u00006\u013e\u0001\u0000\u0000\u00008\u0140"+
		"\u0001\u0000\u0000\u0000:\u0144\u0001\u0000\u0000\u0000<\u0150\u0001\u0000"+
		"\u0000\u0000>\u0152\u0001\u0000\u0000\u0000@\u0176\u0001\u0000\u0000\u0000"+
		"B\u017d\u0001\u0000\u0000\u0000D\u0185\u0001\u0000\u0000\u0000F\u0187"+
		"\u0001\u0000\u0000\u0000H\u0195\u0001\u0000\u0000\u0000J\u01a3\u0001\u0000"+
		"\u0000\u0000L\u01a8\u0001\u0000\u0000\u0000N\u01aa\u0001\u0000\u0000\u0000"+
		"P\u01ac\u0001\u0000\u0000\u0000RS\u0003\u0002\u0001\u0000ST\u0003\u0014"+
		"\n\u0000TU\u0006\u0000\uffff\uffff\u0000U\u0001\u0001\u0000\u0000\u0000"+
		"VW\u0003\b\u0004\u0000WX\u0003\u0002\u0001\u0000Xa\u0001\u0000\u0000\u0000"+
		"YZ\u0003\n\u0005\u0000Z[\u0003\u0002\u0001\u0000[a\u0001\u0000\u0000\u0000"+
		"\\]\u0003\u0012\t\u0000]^\u0003\u0002\u0001\u0000^a\u0001\u0000\u0000"+
		"\u0000_a\u0001\u0000\u0000\u0000`V\u0001\u0000\u0000\u0000`Y\u0001\u0000"+
		"\u0000\u0000`\\\u0001\u0000\u0000\u0000`_\u0001\u0000\u0000\u0000a\u0003"+
		"\u0001\u0000\u0000\u0000bc\u0003\b\u0004\u0000cd\u0003\u0004\u0002\u0000"+
		"dg\u0001\u0000\u0000\u0000eg\u0001\u0000\u0000\u0000fb\u0001\u0000\u0000"+
		"\u0000fe\u0001\u0000\u0000\u0000g\u0005\u0001\u0000\u0000\u0000hi\u0005"+
		"!\u0000\u0000i\u0007\u0001\u0000\u0000\u0000jk\u0003\f\u0006\u0000kl\u0003"+
		"\u0006\u0003\u0000lm\u0005\u0001\u0000\u0000mn\u0006\u0004\uffff\uffff"+
		"\u0000n\t\u0001\u0000\u0000\u0000op\u0005\u0002\u0000\u0000pq\u0003\u0006"+
		"\u0003\u0000qr\u0005\u0003\u0000\u0000rs\u0005$\u0000\u0000st\u0005\u0001"+
		"\u0000\u0000tu\u0006\u0005\uffff\uffff\u0000u\u000b\u0001\u0000\u0000"+
		"\u0000vw\u0006\u0006\uffff\uffff\u0000wx\u0003\u000e\u0007\u0000xy\u0006"+
		"\u0006\uffff\uffff\u0000y\u007f\u0001\u0000\u0000\u0000z{\n\u0001\u0000"+
		"\u0000{|\u0005\u0004\u0000\u0000|~\u0006\u0006\uffff\uffff\u0000}z\u0001"+
		"\u0000\u0000\u0000~\u0081\u0001\u0000\u0000\u0000\u007f}\u0001\u0000\u0000"+
		"\u0000\u007f\u0080\u0001\u0000\u0000\u0000\u0080\r\u0001\u0000\u0000\u0000"+
		"\u0081\u007f\u0001\u0000\u0000\u0000\u0082\u0083\u0005\u0005\u0000\u0000"+
		"\u0083\u0087\u0006\u0007\uffff\uffff\u0000\u0084\u0085\u0005\u0006\u0000"+
		"\u0000\u0085\u0087\u0006\u0007\uffff\uffff\u0000\u0086\u0082\u0001\u0000"+
		"\u0000\u0000\u0086\u0084\u0001\u0000\u0000\u0000\u0087\u000f\u0001\u0000"+
		"\u0000\u0000\u0088\u0089\u0003\f\u0006\u0000\u0089\u008a\u0006\b\uffff"+
		"\uffff\u0000\u008a\u008e\u0001\u0000\u0000\u0000\u008b\u008c\u0005\u0007"+
		"\u0000\u0000\u008c\u008e\u0006\b\uffff\uffff\u0000\u008d\u0088\u0001\u0000"+
		"\u0000\u0000\u008d\u008b\u0001\u0000\u0000\u0000\u008e\u0011\u0001\u0000"+
		"\u0000\u0000\u008f\u0090\u0003\u0010\b\u0000\u0090\u0091\u0003\u0006\u0003"+
		"\u0000\u0091\u0092\u0005\b\u0000\u0000\u0092\u0093\u0003\u0018\f\u0000"+
		"\u0093\u0094\u0005\t\u0000\u0000\u0094\u0095\u0005\u0001\u0000\u0000\u0095"+
		"\u0096\u0006\t\uffff\uffff\u0000\u0096\u0013\u0001\u0000\u0000\u0000\u0097"+
		"\u0098\u0003\u0016\u000b\u0000\u0098\u0099\u0003\u0014\n\u0000\u0099\u009a"+
		"\u0006\n\uffff\uffff\u0000\u009a\u009d\u0001\u0000\u0000\u0000\u009b\u009d"+
		"\u0006\n\uffff\uffff\u0000\u009c\u0097\u0001\u0000\u0000\u0000\u009c\u009b"+
		"\u0001\u0000\u0000\u0000\u009d\u0015\u0001\u0000\u0000\u0000\u009e\u009f"+
		"\u0003\u0010\b\u0000\u009f\u00a0\u0003\u0006\u0003\u0000\u00a0\u00a1\u0005"+
		"\b\u0000\u0000\u00a1\u00a2\u0003\u0018\f\u0000\u00a2\u00a3\u0005\t\u0000"+
		"\u0000\u00a3\u00a4\u0006\u000b\uffff\uffff\u0000\u00a4\u00a5\u0005\n\u0000"+
		"\u0000\u00a5\u00a6\u0003\u0004\u0002\u0000\u00a6\u00a7\u0003\u001e\u000f"+
		"\u0000\u00a7\u00a8\u0005\u000b\u0000\u0000\u00a8\u00a9\u0006\u000b\uffff"+
		"\uffff\u0000\u00a9\u0017\u0001\u0000\u0000\u0000\u00aa\u00ab\u0003\u001c"+
		"\u000e\u0000\u00ab\u00ac\u0003\u001a\r\u0000\u00ac\u00ad\u0006\f\uffff"+
		"\uffff\u0000\u00ad\u00b0\u0001\u0000\u0000\u0000\u00ae\u00b0\u0006\f\uffff"+
		"\uffff\u0000\u00af\u00aa\u0001\u0000\u0000\u0000\u00af\u00ae\u0001\u0000"+
		"\u0000\u0000\u00b0\u0019\u0001\u0000\u0000\u0000\u00b1\u00b2\u0005\f\u0000"+
		"\u0000\u00b2\u00b3\u0003\u001c\u000e\u0000\u00b3\u00b4\u0003\u001a\r\u0000"+
		"\u00b4\u00b5\u0006\r\uffff\uffff\u0000\u00b5\u00b8\u0001\u0000\u0000\u0000"+
		"\u00b6\u00b8\u0006\r\uffff\uffff\u0000\u00b7\u00b1\u0001\u0000\u0000\u0000"+
		"\u00b7\u00b6\u0001\u0000\u0000\u0000\u00b8\u001b\u0001\u0000\u0000\u0000"+
		"\u00b9\u00ba\u0003\f\u0006\u0000\u00ba\u00bb\u0003\u0006\u0003\u0000\u00bb"+
		"\u00bc\u0006\u000e\uffff\uffff\u0000\u00bc\u001d\u0001\u0000\u0000\u0000"+
		"\u00bd\u00be\u0003 \u0010\u0000\u00be\u00bf\u0003\u001e\u000f\u0000\u00bf"+
		"\u00c0\u0006\u000f\uffff\uffff\u0000\u00c0\u00c3\u0001\u0000\u0000\u0000"+
		"\u00c1\u00c3\u0006\u000f\uffff\uffff\u0000\u00c2\u00bd\u0001\u0000\u0000"+
		"\u0000\u00c2\u00c1\u0001\u0000\u0000\u0000\u00c3\u001f\u0001\u0000\u0000"+
		"\u0000\u00c4\u00c5\u0003\"\u0011\u0000\u00c5\u00c6\u0005\u0001\u0000\u0000"+
		"\u00c6\u00c7\u0006\u0010\uffff\uffff\u0000\u00c7\u00cf\u0001\u0000\u0000"+
		"\u0000\u00c8\u00c9\u0003.\u0017\u0000\u00c9\u00ca\u0006\u0010\uffff\uffff"+
		"\u0000\u00ca\u00cf\u0001\u0000\u0000\u0000\u00cb\u00cc\u00032\u0019\u0000"+
		"\u00cc\u00cd\u0006\u0010\uffff\uffff\u0000\u00cd\u00cf\u0001\u0000\u0000"+
		"\u0000\u00ce\u00c4\u0001\u0000\u0000\u0000\u00ce\u00c8\u0001\u0000\u0000"+
		"\u0000\u00ce\u00cb\u0001\u0000\u0000\u0000\u00cf!\u0001\u0000\u0000\u0000"+
		"\u00d0\u00d1\u0003*\u0015\u0000\u00d1\u00d2\u0006\u0011\uffff\uffff\u0000"+
		"\u00d2\u00e0\u0001\u0000\u0000\u0000\u00d3\u00d4\u0003$\u0012\u0000\u00d4"+
		"\u00d5\u0006\u0011\uffff\uffff\u0000\u00d5\u00e0\u0001\u0000\u0000\u0000"+
		"\u00d6\u00d7\u0003&\u0013\u0000\u00d7\u00d8\u0006\u0011\uffff\uffff\u0000"+
		"\u00d8\u00e0\u0001\u0000\u0000\u0000\u00d9\u00da\u0003(\u0014\u0000\u00da"+
		"\u00db\u0006\u0011\uffff\uffff\u0000\u00db\u00e0\u0001\u0000\u0000\u0000"+
		"\u00dc\u00dd\u0003@ \u0000\u00dd\u00de\u0006\u0011\uffff\uffff\u0000\u00de"+
		"\u00e0\u0001\u0000\u0000\u0000\u00df\u00d0\u0001\u0000\u0000\u0000\u00df"+
		"\u00d3\u0001\u0000\u0000\u0000\u00df\u00d6\u0001\u0000\u0000\u0000\u00df"+
		"\u00d9\u0001\u0000\u0000\u0000\u00df\u00dc\u0001\u0000\u0000\u0000\u00e0"+
		"#\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005\r\u0000\u0000\u00e2\u00e3"+
		"\u0005\b\u0000\u0000\u00e3\u00e4\u0003\u0006\u0003\u0000\u00e4\u00e5\u0005"+
		"\t\u0000\u0000\u00e5\u00e6\u0006\u0012\uffff\uffff\u0000\u00e6%\u0001"+
		"\u0000\u0000\u0000\u00e7\u00e8\u0005\u000e\u0000\u0000\u00e8\u00e9\u0005"+
		"\b\u0000\u0000\u00e9\u00ea\u0003F#\u0000\u00ea\u00eb\u0005\t\u0000\u0000"+
		"\u00eb\u00ec\u0006\u0013\uffff\uffff\u0000\u00ec\'\u0001\u0000\u0000\u0000"+
		"\u00ed\u00ee\u0005\u000f\u0000\u0000\u00ee\u00ef\u0003F#\u0000\u00ef\u00f0"+
		"\u0006\u0014\uffff\uffff\u0000\u00f0\u00f4\u0001\u0000\u0000\u0000\u00f1"+
		"\u00f2\u0005\u000f\u0000\u0000\u00f2\u00f4\u0006\u0014\uffff\uffff\u0000"+
		"\u00f3\u00ed\u0001\u0000\u0000\u0000\u00f3\u00f1\u0001\u0000\u0000\u0000"+
		"\u00f4)\u0001\u0000\u0000\u0000\u00f5\u00f6\u0003,\u0016\u0000\u00f6\u00f7"+
		"\u0005\u0003\u0000\u0000\u00f7\u00f8\u0003F#\u0000\u00f8\u00f9\u0006\u0015"+
		"\uffff\uffff\u0000\u00f9+\u0001\u0000\u0000\u0000\u00fa\u00fb\u00034\u001a"+
		"\u0000\u00fb\u00fc\u0006\u0016\uffff\uffff\u0000\u00fc\u0101\u0001\u0000"+
		"\u0000\u0000\u00fd\u00fe\u0003>\u001f\u0000\u00fe\u00ff\u0006\u0016\uffff"+
		"\uffff\u0000\u00ff\u0101\u0001\u0000\u0000\u0000\u0100\u00fa\u0001\u0000"+
		"\u0000\u0000\u0100\u00fd\u0001\u0000\u0000\u0000\u0101-\u0001\u0000\u0000"+
		"\u0000\u0102\u0103\u0005\u0010\u0000\u0000\u0103\u0104\u0005\b\u0000\u0000"+
		"\u0104\u0105\u0003J%\u0000\u0105\u0106\u0005\t\u0000\u0000\u0106\u0107"+
		"\u0005\n\u0000\u0000\u0107\u0108\u0003\u001e\u000f\u0000\u0108\u0109\u0005"+
		"\u000b\u0000\u0000\u0109\u010a\u00030\u0018\u0000\u010a\u010b\u0006\u0017"+
		"\uffff\uffff\u0000\u010b/\u0001\u0000\u0000\u0000\u010c\u010d\u0005\u0011"+
		"\u0000\u0000\u010d\u010e\u0005\n\u0000\u0000\u010e\u010f\u0003\u001e\u000f"+
		"\u0000\u010f\u0110\u0005\u000b\u0000\u0000\u0110\u0111\u0006\u0018\uffff"+
		"\uffff\u0000\u0111\u0114\u0001\u0000\u0000\u0000\u0112\u0114\u0006\u0018"+
		"\uffff\uffff\u0000\u0113\u010c\u0001\u0000\u0000\u0000\u0113\u0112\u0001"+
		"\u0000\u0000\u0000\u01141\u0001\u0000\u0000\u0000\u0115\u0116\u0005\u0012"+
		"\u0000\u0000\u0116\u0117\u0005\b\u0000\u0000\u0117\u0118\u0003J%\u0000"+
		"\u0118\u0119\u0005\t\u0000\u0000\u0119\u011a\u0005\n\u0000\u0000\u011a"+
		"\u011b\u0003\u001e\u000f\u0000\u011b\u011c\u0005\u000b\u0000\u0000\u011c"+
		"\u011d\u0006\u0019\uffff\uffff\u0000\u011d3\u0001\u0000\u0000\u0000\u011e"+
		"\u011f\u0003\u0006\u0003\u0000\u011f\u0120\u0006\u001a\uffff\uffff\u0000"+
		"\u0120\u0125\u0001\u0000\u0000\u0000\u0121\u0122\u0003:\u001d\u0000\u0122"+
		"\u0123\u0006\u001a\uffff\uffff\u0000\u0123\u0125\u0001\u0000\u0000\u0000"+
		"\u0124\u011e\u0001\u0000\u0000\u0000\u0124\u0121\u0001\u0000\u0000\u0000"+
		"\u01255\u0001\u0000\u0000\u0000\u0126\u0127\u00034\u001a\u0000\u0127\u0128"+
		"\u0006\u001b\uffff\uffff\u0000\u0128\u013f\u0001\u0000\u0000\u0000\u0129"+
		"\u012a\u0003<\u001e\u0000\u012a\u012b\u0006\u001b\uffff\uffff\u0000\u012b"+
		"\u013f\u0001\u0000\u0000\u0000\u012c\u012d\u0005\b\u0000\u0000\u012d\u012e"+
		"\u0003F#\u0000\u012e\u012f\u0005\t\u0000\u0000\u012f\u0130\u0006\u001b"+
		"\uffff\uffff\u0000\u0130\u013f\u0001\u0000\u0000\u0000\u0131\u0132\u0003"+
		"8\u001c\u0000\u0132\u0133\u0006\u001b\uffff\uffff\u0000\u0133\u013f\u0001"+
		"\u0000\u0000\u0000\u0134\u0135\u0003@ \u0000\u0135\u0136\u0006\u001b\uffff"+
		"\uffff\u0000\u0136\u013f\u0001\u0000\u0000\u0000\u0137\u0138\u0003>\u001f"+
		"\u0000\u0138\u0139\u0006\u001b\uffff\uffff\u0000\u0139\u013f\u0001\u0000"+
		"\u0000\u0000\u013a\u013b\u0005\"\u0000\u0000\u013b\u013f\u0006\u001b\uffff"+
		"\uffff\u0000\u013c\u013d\u0005#\u0000\u0000\u013d\u013f\u0006\u001b\uffff"+
		"\uffff\u0000\u013e\u0126\u0001\u0000\u0000\u0000\u013e\u0129\u0001\u0000"+
		"\u0000\u0000\u013e\u012c\u0001\u0000\u0000\u0000\u013e\u0131\u0001\u0000"+
		"\u0000\u0000\u013e\u0134\u0001\u0000\u0000\u0000\u013e\u0137\u0001\u0000"+
		"\u0000\u0000\u013e\u013a\u0001\u0000\u0000\u0000\u013e\u013c\u0001\u0000"+
		"\u0000\u0000\u013f7\u0001\u0000\u0000\u0000\u0140\u0141\u0005\u0013\u0000"+
		"\u0000\u0141\u0142\u0003F#\u0000\u0142\u0143\u0006\u001c\uffff\uffff\u0000"+
		"\u01439\u0001\u0000\u0000\u0000\u0144\u0145\u0005\u0004\u0000\u0000\u0145"+
		"\u0146\u00036\u001b\u0000\u0146\u0147\u0006\u001d\uffff\uffff\u0000\u0147"+
		";\u0001\u0000\u0000\u0000\u0148\u0149\u0005\u0014\u0000\u0000\u0149\u014a"+
		"\u00034\u001a\u0000\u014a\u014b\u0006\u001e\uffff\uffff\u0000\u014b\u0151"+
		"\u0001\u0000\u0000\u0000\u014c\u014d\u0005\u0014\u0000\u0000\u014d\u014e"+
		"\u0003>\u001f\u0000\u014e\u014f\u0006\u001e\uffff\uffff\u0000\u014f\u0151"+
		"\u0001\u0000\u0000\u0000\u0150\u0148\u0001\u0000\u0000\u0000\u0150\u014c"+
		"\u0001\u0000\u0000\u0000\u0151=\u0001\u0000\u0000\u0000\u0152\u0153\u0006"+
		"\u001f\uffff\uffff\u0000\u0153\u0154\u00034\u001a\u0000\u0154\u0155\u0005"+
		"\u0015\u0000\u0000\u0155\u0156\u0003F#\u0000\u0156\u0157\u0005\u0016\u0000"+
		"\u0000\u0157\u0158\u0006\u001f\uffff\uffff\u0000\u0158\u0161\u0001\u0000"+
		"\u0000\u0000\u0159\u015a\n\u0001\u0000\u0000\u015a\u015b\u0005\u0015\u0000"+
		"\u0000\u015b\u015c\u0003F#\u0000\u015c\u015d\u0005\u0016\u0000\u0000\u015d"+
		"\u015e\u0006\u001f\uffff\uffff\u0000\u015e\u0160\u0001\u0000\u0000\u0000"+
		"\u015f\u0159\u0001\u0000\u0000\u0000\u0160\u0163\u0001\u0000\u0000\u0000"+
		"\u0161\u015f\u0001\u0000\u0000\u0000\u0161\u0162\u0001\u0000\u0000\u0000"+
		"\u0162?\u0001\u0000\u0000\u0000\u0163\u0161\u0001\u0000\u0000\u0000\u0164"+
		"\u0165\u0005\u0017\u0000\u0000\u0165\u0166\u0005\b\u0000\u0000\u0166\u0167"+
		"\u0003F#\u0000\u0167\u0168\u0005\t\u0000\u0000\u0168\u0169\u0006 \uffff"+
		"\uffff\u0000\u0169\u0177\u0001\u0000\u0000\u0000\u016a\u016b\u0005\u0018"+
		"\u0000\u0000\u016b\u016c\u0005\b\u0000\u0000\u016c\u016d\u0003F#\u0000"+
		"\u016d\u016e\u0005\t\u0000\u0000\u016e\u016f\u0006 \uffff\uffff\u0000"+
		"\u016f\u0177\u0001\u0000\u0000\u0000\u0170\u0171\u0003\u0006\u0003\u0000"+
		"\u0171\u0172\u0005\b\u0000\u0000\u0172\u0173\u0003B!\u0000\u0173\u0174"+
		"\u0005\t\u0000\u0000\u0174\u0175\u0006 \uffff\uffff\u0000\u0175\u0177"+
		"\u0001\u0000\u0000\u0000\u0176\u0164\u0001\u0000\u0000\u0000\u0176\u016a"+
		"\u0001\u0000\u0000\u0000\u0176\u0170\u0001\u0000\u0000\u0000\u0177A\u0001"+
		"\u0000\u0000\u0000\u0178\u0179\u0003F#\u0000\u0179\u017a\u0003D\"\u0000"+
		"\u017a\u017b\u0006!\uffff\uffff\u0000\u017b\u017e\u0001\u0000\u0000\u0000"+
		"\u017c\u017e\u0006!\uffff\uffff\u0000\u017d\u0178\u0001\u0000\u0000\u0000"+
		"\u017d\u017c\u0001\u0000\u0000\u0000\u017eC\u0001\u0000\u0000\u0000\u017f"+
		"\u0180\u0005\f\u0000\u0000\u0180\u0181\u0003F#\u0000\u0181\u0182\u0003"+
		"D\"\u0000\u0182\u0183\u0006\"\uffff\uffff\u0000\u0183\u0186\u0001\u0000"+
		"\u0000\u0000\u0184\u0186\u0006\"\uffff\uffff\u0000\u0185\u017f\u0001\u0000"+
		"\u0000\u0000\u0185\u0184\u0001\u0000\u0000\u0000\u0186E\u0001\u0000\u0000"+
		"\u0000\u0187\u0188\u0006#\uffff\uffff\u0000\u0188\u0189\u0003H$\u0000"+
		"\u0189\u018a\u0006#\uffff\uffff\u0000\u018a\u0192\u0001\u0000\u0000\u0000"+
		"\u018b\u018c\n\u0001\u0000\u0000\u018c\u018d\u0003P(\u0000\u018d\u018e"+
		"\u0003H$\u0000\u018e\u018f\u0006#\uffff\uffff\u0000\u018f\u0191\u0001"+
		"\u0000\u0000\u0000\u0190\u018b\u0001\u0000\u0000\u0000\u0191\u0194\u0001"+
		"\u0000\u0000\u0000\u0192\u0190\u0001\u0000\u0000\u0000\u0192\u0193\u0001"+
		"\u0000\u0000\u0000\u0193G\u0001\u0000\u0000\u0000\u0194\u0192\u0001\u0000"+
		"\u0000\u0000\u0195\u0196\u0006$\uffff\uffff\u0000\u0196\u0197\u00036\u001b"+
		"\u0000\u0197\u0198\u0006$\uffff\uffff\u0000\u0198\u01a0\u0001\u0000\u0000"+
		"\u0000\u0199\u019a\n\u0001\u0000\u0000\u019a\u019b\u0003N\'\u0000\u019b"+
		"\u019c\u00036\u001b\u0000\u019c\u019d\u0006$\uffff\uffff\u0000\u019d\u019f"+
		"\u0001\u0000\u0000\u0000\u019e\u0199\u0001\u0000\u0000\u0000\u019f\u01a2"+
		"\u0001\u0000\u0000\u0000\u01a0\u019e\u0001\u0000\u0000\u0000\u01a0\u01a1"+
		"\u0001\u0000\u0000\u0000\u01a1I\u0001\u0000\u0000\u0000\u01a2\u01a0\u0001"+
		"\u0000\u0000\u0000\u01a3\u01a4\u0003F#\u0000\u01a4\u01a5\u0003L&\u0000"+
		"\u01a5\u01a6\u0003F#\u0000\u01a6\u01a7\u0006%\uffff\uffff\u0000\u01a7"+
		"K\u0001\u0000\u0000\u0000\u01a8\u01a9\u0007\u0000\u0000\u0000\u01a9M\u0001"+
		"\u0000\u0000\u0000\u01aa\u01ab\u0007\u0001\u0000\u0000\u01abO\u0001\u0000"+
		"\u0000\u0000\u01ac\u01ad\u0007\u0002\u0000\u0000\u01adQ\u0001\u0000\u0000"+
		"\u0000\u0017`f\u007f\u0086\u008d\u009c\u00af\u00b7\u00c2\u00ce\u00df\u00f3"+
		"\u0100\u0113\u0124\u013e\u0150\u0161\u0176\u017d\u0185\u0192\u01a0";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}