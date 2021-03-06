package sizzle.compiler;

import java.io.IOException;

import sizzle.parser.syntaxtree.ArrayType;
import sizzle.parser.syntaxtree.Assignment;
import sizzle.parser.syntaxtree.Block;
import sizzle.parser.syntaxtree.BreakStatement;
import sizzle.parser.syntaxtree.BytesLiteral;
import sizzle.parser.syntaxtree.Call;
import sizzle.parser.syntaxtree.CharLiteral;
import sizzle.parser.syntaxtree.Comparison;
import sizzle.parser.syntaxtree.Component;
import sizzle.parser.syntaxtree.Composite;
import sizzle.parser.syntaxtree.Conjunction;
import sizzle.parser.syntaxtree.ContinueStatement;
import sizzle.parser.syntaxtree.Declaration;
import sizzle.parser.syntaxtree.DoStatement;
import sizzle.parser.syntaxtree.EmitStatement;
import sizzle.parser.syntaxtree.ExprList;
import sizzle.parser.syntaxtree.ExprStatement;
import sizzle.parser.syntaxtree.Expression;
import sizzle.parser.syntaxtree.Factor;
import sizzle.parser.syntaxtree.FingerprintLiteral;
import sizzle.parser.syntaxtree.FloatingPointLiteral;
import sizzle.parser.syntaxtree.ForExprStatement;
import sizzle.parser.syntaxtree.ForStatement;
import sizzle.parser.syntaxtree.ForVarDecl;
import sizzle.parser.syntaxtree.Function;
import sizzle.parser.syntaxtree.FunctionType;
import sizzle.parser.syntaxtree.Identifier;
import sizzle.parser.syntaxtree.IdentifierList;
import sizzle.parser.syntaxtree.IfStatement;
import sizzle.parser.syntaxtree.Index;
import sizzle.parser.syntaxtree.IntegerLiteral;
import sizzle.parser.syntaxtree.MapType;
import sizzle.parser.syntaxtree.Node;
import sizzle.parser.syntaxtree.NodeChoice;
import sizzle.parser.syntaxtree.Operand;
import sizzle.parser.syntaxtree.OutputType;
import sizzle.parser.syntaxtree.Pair;
import sizzle.parser.syntaxtree.PairList;
import sizzle.parser.syntaxtree.Program;
import sizzle.parser.syntaxtree.Proto;
import sizzle.parser.syntaxtree.ProtoFieldDecl;
import sizzle.parser.syntaxtree.ProtoMember;
import sizzle.parser.syntaxtree.ProtoMemberList;
import sizzle.parser.syntaxtree.ProtoTupleType;
import sizzle.parser.syntaxtree.Regexp;
import sizzle.parser.syntaxtree.RegexpList;
import sizzle.parser.syntaxtree.ResultStatement;
import sizzle.parser.syntaxtree.ReturnStatement;
import sizzle.parser.syntaxtree.Selector;
import sizzle.parser.syntaxtree.SimpleExpr;
import sizzle.parser.syntaxtree.SimpleMember;
import sizzle.parser.syntaxtree.SimpleMemberList;
import sizzle.parser.syntaxtree.SimpleTupleType;
import sizzle.parser.syntaxtree.Start;
import sizzle.parser.syntaxtree.Statement;
import sizzle.parser.syntaxtree.StatementExpr;
import sizzle.parser.syntaxtree.StaticVarDecl;
import sizzle.parser.syntaxtree.StringLiteral;
import sizzle.parser.syntaxtree.SwitchStatement;
import sizzle.parser.syntaxtree.Term;
import sizzle.parser.syntaxtree.TimeLiteral;
import sizzle.parser.syntaxtree.TupleType;
import sizzle.parser.syntaxtree.Type;
import sizzle.parser.syntaxtree.TypeDecl;
import sizzle.parser.syntaxtree.VarDecl;
import sizzle.parser.syntaxtree.WhenStatement;
import sizzle.parser.syntaxtree.WhileStatement;
import sizzle.parser.visitor.GJDepthFirst;

/**
 * Prescan the Sizzle program and generate initializer code for any static
 * variables.
 * 
 * @author anthonyu
 * 
 */
public class StaticDeclarationCodeGeneratingVisitor extends GJDepthFirst<String, SymbolTable> {
	private final CodeGeneratingVisitor codegenerator;

	public StaticDeclarationCodeGeneratingVisitor(final CodeGeneratingVisitor codegenerator) throws IOException {
		this.codegenerator = codegenerator;
	}

	@Override
	public String visit(final Start n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Program n, final SymbolTable argu) {
		final StringBuilder sb = new StringBuilder();

		for (final Node node : n.f0.nodes) {
			final NodeChoice nodeChoice = (NodeChoice) node;
			switch (nodeChoice.which) {
			case 0: // declaration
				final String accept = nodeChoice.choice.accept(this, argu);

				if (accept != null)
					sb.append(accept);
				break;
			case 1: // statement
				break;
			case 2: // proto
			default:
				throw new RuntimeException("unexpected choice " + nodeChoice.which + " is " + nodeChoice.choice.getClass());
			}
		}

		return sb.toString();
	}

	@Override
	public String visit(final Declaration n, final SymbolTable argu) {
		switch (n.f0.which) {
		case 0: // type declaration
			return null;
		case 1: // static var declaration
			return n.f0.choice.accept(this, argu);
		case 2: // variable declaration
			return null;
		default:
			throw new RuntimeException("unexpected choice " + n.f0.which + " is " + n.f0.choice.getClass());
		}
	}

	@Override
	public String visit(final TypeDecl n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final StaticVarDecl n, final SymbolTable argu) {
		return "private static " + this.codegenerator.visit(n.f1, argu);
	}

	@Override
	public String visit(final VarDecl n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Type n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Component n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final ArrayType n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final TupleType n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final SimpleTupleType n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final SimpleMemberList n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final SimpleMember n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final ProtoTupleType n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final ProtoMemberList n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final ProtoMember n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final ProtoFieldDecl n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final MapType n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final OutputType n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final ExprList n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final FunctionType n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Statement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Assignment n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Block n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final BreakStatement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final ContinueStatement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final DoStatement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final EmitStatement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final ExprStatement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final ForStatement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final ForVarDecl n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final ForExprStatement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final IfStatement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final ResultStatement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final ReturnStatement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final SwitchStatement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final WhenStatement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final IdentifierList n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final WhileStatement n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Expression n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Conjunction n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Comparison n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final SimpleExpr n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Term n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Factor n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Selector n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Index n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Call n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final RegexpList n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Regexp n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Operand n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Composite n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final PairList n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Pair n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Function n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final StatementExpr n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Proto n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final Identifier n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final IntegerLiteral n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final FingerprintLiteral n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final FloatingPointLiteral n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final CharLiteral n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final StringLiteral n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final BytesLiteral n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	@Override
	public String visit(final TimeLiteral n, final SymbolTable argu) {
		throw new RuntimeException("unimplemented");
	}

	// private final NameFindingVisitor namefinder;
	//
	// /**
	// * Construct a StaticDeclarationCodeGeneratingVisitor.
	// *
	// * @param namefinder
	// * A {@link NameFindingVisitor} used to find names.
	// */
	// public StaticDeclarationCodeGeneratingVisitor(final NameFindingVisitor
	// namefinder) {
	// this.namefinder = namefinder;
	// }
	//
	// /** {@inheritDoc} */
	// @Override
	// public String visit(final Pair n, final SymbolTable argu) {
	// return argu.getMapName() + ".put(" + n.f0.accept(this, argu) + ", " +
	// n.f2.accept(this, argu) + ");\n";
	// }
	//
	// /** {@inheritDoc} */
	// @Override
	// public String visit(final PairList n, final SymbolTable argu) {
	// final StringBuilder src = new StringBuilder();
	//
	// src.append(n.f0.accept(this, argu));
	//
	// if (n.f1.present())
	// for (final Node node : n.f1.nodes)
	// src.append(node.accept(this, argu));
	//
	// return src.toString();
	// }
	//
	// /** {@inheritDoc} */
	// @Override
	// public String visit(final Start n, final SymbolTable argu) {
	// return n.f0.accept(this, argu);
	// }
	//
	// /** {@inheritDoc} */
	// @Override
	// public String visit(final Program n, final SymbolTable argu) {
	// final StringBuilder statements = new StringBuilder();
	//
	// for (final Node i : n.f0.nodes) {
	// final String src = ((Statement) i).accept(this, argu);
	// if (src != null && !src.equals(""))
	// statements.append(src + "\n");
	// }
	//
	// return statements.toString();
	// }
	//
	// /** {@inheritDoc} */
	// @Override
	// public String visit(final Statement n, final SymbolTable argu) {
	// switch (n.f0.which) {
	// case 2: // static variable declaration
	// return ((VariableDeclaration) n.f0.choice).accept(this, argu);
	// default:
	// return "";
	// }
	// }
	//
	// /** {@inheritDoc} */
	// @Override
	// public String visit(final StaticVariableDeclaration n, final SymbolTable
	// argu) {
	// return n.f1.accept(this, argu);
	// }
	//
	// /** {@inheritDoc} */
	// @Override
	// public String visit(final VariableDeclaration n, final SymbolTable argu)
	// {
	// final StringBuilder src = new StringBuilder();
	//
	// for (final String id : this.namefinder.visit(n.f1)) {
	// final SizzleType sizzleType = argu.get(id);
	//
	// final String javaType = sizzleType.toJavaType();
	//
	// if (n.f3.present()) {
	// argu.setInitializerType(sizzleType);
	// argu.setMapName("___" + id);
	// final String initializer = ((Initializer) ((NodeSequence)
	// n.f3.node).elementAt(1)).accept(this, argu);
	// argu.setMapName(null);
	// argu.setInitializerType(null);
	//
	// src.append("private static final " + javaType + " ___" + id + " = " +
	// initializer + ";");
	// if (sizzleType.getClass().equals(SizzleMap.class)) {
	// src.append("\n{\n" + argu.getStaticInitializer() + "\n}\n");
	// }
	// } else {
	// throw new RuntimeException("static variables must be initialized");
	// }
	//
	// }
	//
	// return src.toString();
	// }
}
