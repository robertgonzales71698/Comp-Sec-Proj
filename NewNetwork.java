import org.encog.util.simple.EncogUtility;
import org.encog.util.simple.TrainingSetUtil;
import org.encog.Encog;
import org.encog.ml.data.MLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.util.csv.CSVFormat;


 class makeNN {

	public static void main(String args[]) {

		if (args.length == 0) {
			System.out.println("Enter CSV filename.");
		} else {
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
