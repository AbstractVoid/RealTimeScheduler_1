/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RealTimeScheduler;

/**
 *
 * @author Nicholas
 */
import java.util.ArrayList;
import java.util.Random;

public class ComputeResourceGenerator {

    private int maxCreate;
    private Random rng;

    public ComputeResourceGenerator(int max) {
        maxCreate = max;
        rng = new Random();
    }

    public ArrayList<ComputeResource> getResources() {
        ArrayList<ComputeResource> res = new ArrayList<ComputeResource>();
        int num = rng.nextInt() % maxCreate;
        for (int i = 0; i < num + 1; i++) {
            res.add(new ComputeResource(1));
        }
        return res;
    }
}