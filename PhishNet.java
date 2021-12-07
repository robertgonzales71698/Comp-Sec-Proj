//have this up as placeholder right now, fixing up a lot of stuff in Eclipse


package Phishnet;

import java.io.File;
import java.util.Arrays;
import org.encog.engine.network.activation.*;
import org.encog.neural.networks.*;
import org.encog.neural.networks.layers.*;
import org.encog.neural.pattern.*;
import org.encog.ConsoleStatusReportable;
import org.encog.Encog;
import org.encog.bot.BotUtil;
import org.encog.ml.MLRegression;
import org.encog.ml.data.MLData;
import org.encog.ml.data.versatile.NormalizationHelper;
import org.encog.ml.data.versatile.VersatileMLDataSet;
import org.encog.ml.data.versatile.columns.ColumnDefinition;
import org.encog.ml.data.versatile.columns.ColumnType;
import org.encog.ml.data.versatile.sources.CSVDataSource;
import org.encog.ml.data.versatile.sources.VersatileDataSource;
import org.encog.ml.factory.MLMethodFactory;
import org.encog.ml.model.EncogModel;
import org.encog.util.csv.CSVFormat;
import org.encog.util.csv.ReadCSV;
import org.encog.util.simple.EncogUtility;

public class PhishNet {
	
	//Put the data here
	public File emails;
	
	//placeholder for datasource
	VersatileDataSource dbase = new CSVDataSource(emails, false, CSVFormat.DECIMAL_POINT);
	VersatileMLDataSet data = new VersatileMLDataSet(dbase);
	
	//method to set up the network
	public void nnsetup() {
		BasicNetwork mnetwork = new BasicNetwork(); 
		mnetwork.addLayer(new BasicLayer(null,true,5));
		mnetwork.addLayer(new BasicLayer(new ActivationSigmoid(), true, 8));
		mnetwork.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));
		mnetwork.getStructure().finalizeStructure();
		mnetwork.reset();
	}
	
}

