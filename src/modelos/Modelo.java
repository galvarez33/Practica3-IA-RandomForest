/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes; //no hace falta en teoria
import weka.classifiers.functions.MultilayerPerceptron; // no hace falta en teoria
import weka.classifiers.trees.J48; //no hace falta en teoria
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.functions.*;

/**
 *
 * @author mariano
 */
public class Modelo {

    private Instances leerInstancias(String ficherArff){
        try{
            Instances inst = new Instances(new BufferedReader(new FileReader(ficherArff)));
            inst.setClassIndex(inst.numAttributes() - 1);

            return inst;
        }catch (IOException ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public void aprenderModelo()
    {
        try {
            // create model
            Classifier cls = new RandomForest();

            // train
            Instances inst = leerInstancias("./test_data/juegosdelhambre.arff");
            cls.buildClassifier(inst);

            // serialize model
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./models/juegosdelhambre.model"));
            oos.writeObject(cls);
            oos.flush();
            oos.close();
        } catch (Exception ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String aplicarModelo(int distrito, boolean sexo, int edad, int voluntario, int career, int rating, int dias_sobrevividos, int ganador) {
        try{
            Classifier clasificador  = (Classifier) weka.core.SerializationHelper.read("./models/juegosdelhambre.model");
            Instances data = leerInstancias("./test_data/juegosdelhambre.arff");
            return valoresAtributos[(int) clasificador.classifyInstance(data.instance(0))]; //falla porque está incompleto
        }catch (Exception ex) {
            Logger.getLogger(Modelo.class.getName()).log(Level.SEVERE, null, ex);
            return "Error al intentar leer el modelo";
        }
    }
}
