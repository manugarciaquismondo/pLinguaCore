package org.gcn.plinguacore.parser.output.simplekernel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.gcn.plinguacore.parser.output.OutputParser;
import org.gcn.plinguacore.parser.output.simplekernel.KernelDictionaryWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelHeaderWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelMapper;
import org.gcn.plinguacore.parser.output.simplekernel.KernelMembraneWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelNumberWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelObjectWriter;
import org.gcn.plinguacore.parser.output.simplekernel.KernelRuleWriter;
import org.gcn.plinguacore.parser.output.probabilisticGuarded.ProbabilisticGuardedFlagWriter;
import org.gcn.plinguacore.parser.output.probabilisticGuarded.ProbabilisticGuardedHeaderWriter;
import org.gcn.plinguacore.parser.output.probabilisticGuarded.ProbabilisticGuardedMembraneWriter;
import org.gcn.plinguacore.parser.output.probabilisticGuarded.ProbabilisticGuardedRuleWriter;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.probabilisticGuarded.ProbabilisticGuardedPsystem;
import org.gcn.plinguacore.util.psystem.simplekernel.SimpleKernelLikePsystem;

public class KernelOutputParser extends DictionariedOutputParser {

	


	KernelMembraneWriter membraneWriter;

	protected StringBuffer psystemDescription;

	protected KernelHeaderWriter headerWriter;

	KernelObjectWriter objectWriter;

	protected KernelRuleWriter ruleWriter;

	
	private KernelNumberWriter numberWriter;
	
	protected ProbabilisticGuardedFlagWriter flagWriter;

	protected SimpleKernelLikePsystem psystem;


	@Override
	public boolean parse(Psystem psystem, OutputStream stream) {
		// TODO Auto-generated method stub
		super.parse(psystem, stream);
		OutputStreamWriter writer = new OutputStreamWriter(stream);
		return parse(psystem, writer);

	}

	private boolean checkAndInitializePsystem(Psystem psystem) {
		if (psystem == null || !(psystem instanceof SimpleKernelLikePsystem))
			return false;
		this.psystem = (SimpleKernelLikePsystem) psystem;
		initializePsystem();		
		return true;

	}


	protected void initializePsystem() {		
		
		numberWriter =new KernelNumberWriter();
		headerWriter = createHeaderWriter();
		objectWriter = new KernelObjectWriter(this.psystem, mapper);
		membraneWriter = createMembraneWriter();
		ruleWriter = createKernelRuleWriter();
		flagWriter = new ProbabilisticGuardedFlagWriter(this.psystem, this);
		mapper.createMappings();
		numberWriter.setMapper(mapper);
	}

	protected KernelMembraneWriter createMembraneWriter() {
		return new KernelMembraneWriter(this.psystem, this);
	}

	protected KernelHeaderWriter createHeaderWriter() {
		return new KernelHeaderWriter(this.psystem);
	}

	protected KernelRuleWriter createKernelRuleWriter() {
		return new KernelRuleWriter(this, this.psystem);
	}

	@Override
	public boolean parse(Psystem psystem, Writer stream) {
		// TODO Auto-generated method stub
		super.parse(psystem, stream);
		if (!checkAndInitializePsystem(psystem))
			return false;
		
		boolean result = writeStream(stream);
		result = result&&writeDictionary();
		return result;
		// stream.append(transformToString());

	}

	public KernelOutputParser() {
		super();
		
		// TODO Auto-generated constructor stub
	}



	protected boolean writeStream(Writer stream) {
		try {
			stream.append(transformToString());
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Errors produced while parsing the P system");
			return false;
		}
		return true;

	}

	protected CharSequence transformToString() {
		// TODO Auto-generated method stub
		psystemDescription = new StringBuffer();
		addHeader();
		addInitialConfiguration();	
		addFlags();
		addRules();
		return psystemDescription;
	}


	protected void addFlags() {
			
		
	}

	private void addInitialConfiguration() {
		membraneWriter.addInitialConfiguration(psystemDescription);

	}

	private void addRules() {
		ruleWriter.addRules(psystemDescription);

	}

	private void addHeader() {

		headerWriter.addHeader(psystemDescription);

	}





}
