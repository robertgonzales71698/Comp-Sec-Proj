import org.encog.util.simple.EncogUtility;
import org.encog.util.simple.TrainingSetUtil;
import org.encog.Encog;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.csv.CSVFormat;
import java.io.File;
import org.encog.Encog;
import org.encog.app.analyst.AnalystFileFormat;
import org.encog.app.analyst.EncogAnalyst;
import org.encog.app.analyst.csv.normalize.AnalystNormalizeCSV;
import org.encog.app.analyst.script.normalize.AnalystField;
import org.encog.app.analyst.wizard.AnalystWizard;
import org.encog.util.arrayutil.NormalizationAction;
import org.encog.util.csv.CSVFormat;


 class makeNN {
	//Normalizing related function from Encog
	public static void dumpFieldInfo(EncogAnalyst analyst) {
		System.out.println("Fields found in file:");
		for (AnalystField field : analyst.getScript().getNormalize()
				.getNormalizedFields()) {

			StringBuilder line = new StringBuilder();
			line.append(field.getName());
			line.append(",action=");
			line.append(field.getAction());
			line.append(",min=");
			line.append(field.getActualLow());
			line.append(",max=");
			line.append(field.getActualHigh());
			System.out.println(line.toString());
		}
	}

	public static void main(String args[]) {

		if (args.length == 0) {
			System.out.println("Enter CSV filename.");
		} else {
			//Normalize the input
			File sourceFile = new File(args[0]);

			EncogAnalyst analyst = new EncogAnalyst();
			AnalystWizard wizard = new AnalystWizard(analyst);
			wizard.wizard(sourceFile, true, AnalystFileFormat.DECPNT_COMMA);

			dumpFieldInfo(analyst);

			final AnalystNormalizeCSV norm = new AnalystNormalizeCSV();
			norm.analyze(sourceFile, true, CSVFormat.ENGLISH, analyst);
			norm.setProduceOutputHeaders(true);
			norm.normalize(sourceFile);
			Encog.getInstance().shutdown();
			//create the dataset from the input
			final MLDataSet trainingSet = TrainingSetUtil.loadCSVTOMemory(
					CSVFormat.ENGLISH, args[0], false, 2, 1);
			
			//create the neural network
			final BasicNetwork network = EncogUtility.simpleFeedForward(12, 18,
					0, 2, true);
			//train the network
			System.out.println();
			System.out.println("Training the new neural network");
			EncogUtility.trainToError(network, trainingSet, 0.1);
			//evaluate the network
			System.out.println();
			System.out.println("Evaluating the new neural network");
			EncogUtility.evaluate(network, trainingSet);
		}
		Encog.getInstance().shutdown();
	}
}
