import java.io.File;
import org.encog.Encog;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.persist.EncogDirectoryPersistence;
import org.encog.util.simple.EncogUtility;

public class PhishNet2 {

	//Original input training data
	public static final double[][] INPUT_DATA = {
        { 1,-1,-1,1,-1,1,-1,-0.8509316770185483,1,-1,-0.8660254037844386,-0.5 },
        { -1,1,-1,1,-1,1,-1,-0.6734693877549154,1,-1,0,1 },
        { -1,1,-1,1,1,-1,-0.5,0.30612244898033825,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,-0.37662337662302037,1,-1,0,1 },
        { -1,1,-1,1,-1,1,-0.6,-0.5966386554619544,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.714285714285551,1,-1,-0.8660254037844386,-0.5 },
        { -1,1,-1,1,-1,1,-1,-0.819548872180348,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,-0.4285714285711021,1,-1,-0.8660254037844386,-0.5 },
        { -1,1,-1,1,-1,1,-0.6,-0.7983193277309772,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,-1,1,1,-1,-1,0.37142857142935526,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,0.14285714285779583,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,-0.4285714285711021,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,-0.2906403940882646,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,0,-0.6190476190474014,1,-1,0,1 },
        { 1,-1,1,-1,-1,1,1,-0.2906403940882646,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-1,1,-1,0,1 },
        { 1,-1,1,-1,-1,1,0,-0.5966386554619544,1,-1,0,1 },
        { 1,-1,1,-1,-1,1,1,-0.6883116883115101,1,-1,0,1 },
        { 1,-1,-1,1,-1,1,-1,-0.8441558441557551,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,-0.714285714285551,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.7983193277309772,1,-1,0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.5714285714283265,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,0,-0.5966386554619544,1,-1,0,1 },
        { 1,-1,-1,1,-1,1,-1,-1,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,-0.37662337662302037,1,-1,0,1 },
        { 1,-1,1,-1,-1,1,1,-0.6571428571426612,1,-1,0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,0,-0.08571428571376316,-1,1,-0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.40886699507355373,1,-1,-0.8660254037844386,-0.5 },
        { -1,1,1,-1,-1,1,0,-0.458646616541044,1,-1,-0.8660254037844386,-0.5 },
        { -1,1,-1,1,-1,1,-0.6,-0.5966386554619544,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,-0.714285714285551,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,-0.4285714285711021,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,0.05494505494565782,-1,1,-0.8660254037844386,-0.5 },
        { -1,1,-1,1,-1,1,-1,-1,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,0,-0.7983193277309772,1,-1,0.8660254037844386,-0.5 },
        { 1,-1,1,-1,1,-1,1,0.7142857142866941,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,-0.6883116883115101,1,-1,0,1 },
        { 1,-1,-1,1,-1,1,-1,-1,1,-1,0,1 },
        { 1,-1,1,-1,-1,1,1,-0.4285714285711021,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.714285714285551,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,1,-1,1,0.7142857142866941,1,-1,-0.8660254037844386,-0.5 },
        { -1,1,-1,1,-1,1,-1,-1,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,0,-0.08571428571376316,-1,1,-0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.6453201970441322,1,-1,-0.8660254037844386,-0.5 },
        { -1,1,-1,1,-1,1,-1,-1,1,-1,0,1 },
        { 1,-1,-1,1,-1,1,-1,-0.4285714285711021,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.40886699507355373,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,-0.6883116883115101,1,-1,0,1 },
        { 1,-1,-1,1,-1,1,-1,-1,1,-1,0,1 },
        { -1,1,-1,1,-1,1,-1,-1,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.6190476190474014,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,0,-0.7983193277309772,1,-1,0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.4285714285711021,-1,1,0.8660254037844386,-0.5 },
        { 1,-1,-1,1,1,-1,-1,0.37142857142935526,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,-0.6571428571426612,1,-1,0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.6190476190474014,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,1,-1,1,1.000000000001143,1,-1,-0.8660254037844386,-0.5 },
        { -1,1,-1,1,-1,1,-1,-1,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.4285714285711021,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,1,-1,-1,1,1,-0.6883116883115101,1,-1,0,1 },
        { -1,1,-1,1,-1,1,-1,-0.7983193277309772,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.7460317460316009,-1,1,-0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.4285714285711021,-1,1,0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.6190476190474014,1,-1,-0.8660254037844386,-0.5 },
        { 1,-1,-1,1,-1,1,-1,-0.6190476190474014,1,-1,-0.8660254037844386,-0.5 },
        { -1,1,-1,1,-1,1,-1,-0.8441558441557551,-1,1,0,1 },
    };
//Original input target data
    public static final double[][] IDEAL_DATA = {
        { -1,1 },
        { 1,-1 },
        { 1,-1 },
        { 1,-1 },
        { -1,1 },
        { 1,-1 },
        { -1,1 },
        { -1,1 },
        { -1,1 },
        { -1,1 },
        { 1,-1 },
        { -1,1 },
        { 1,-1 },
        { 1,-1 },
        { 1,-1 },
        { 1,-1 },
        { 1,-1 },
        { 1,-1 },
        { -1,1 },
        { -1,1 },
        { -1,1 },
        { -1,1 },
        { 1,-1 },
        { -1,1 },
        { 1,-1 },
        { -1,1 },
        { 1,-1 },
        { 1,-1 },
        { -1,1 },
        { -1,1 },
        { -1,1 },
        { -1,1 },
        { 1,-1 },
        { -1,1 },
        { -1,1 },
        { -1,1 },
        { 1,-1 },
        { 1,-1 },
        { -1,1 },
        { 1,-1 },
        { 1,-1 },
        { -1,1 },
        { 1,-1 },
        { 1,-1 },
        { 1,-1 },
        { -1,1 },
        { 1,-1 },
        { 1,-1 },
        { 1,-1 },
        { -1,1 },
        { -1,1 },
        { -1,1 },
        { 1,-1 },
        { -1,1 },
        { -1,1 },
        { -1,1 },
        { 1,-1 },
        { -1,1 },
        { -1,1 },
        { 1,-1 },
        { -1,1 },
        { 1,-1 },
        { 1,-1 },
        { 1,-1 },
        { 1,-1 },
        { 1,-1 },
    };

	
	//This loads the neural network from the EG file
	public void loadAndEvaluate() {
		System.out.println("Loading network");
		//Loads the network from an EG file
		BasicNetwork network = (BasicNetwork)EncogDirectoryPersistence.loadObject(new File("results-copy.eg"));
		
		//sets the training set back up for evaluation and displays the training error
		MLDataSet trainingSet = new BasicMLDataSet(INPUT_DATA, IDEAL_DATA);
		double e = network.calculateError(trainingSet);
		System.out
				.println("This network's error is: "
						+ e);
	}

}
