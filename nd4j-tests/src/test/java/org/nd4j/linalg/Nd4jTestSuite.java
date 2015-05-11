package org.nd4j.linalg;

import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.AllTests;
import org.nd4j.linalg.factory.Nd4jBackend;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * Created by agibsoncccc on 5/11/15.
 */

@RunWith(AllTests.class)

public class Nd4jTestSuite {



    public static TestSuite suite() throws Exception  {
        TestSuite testSuite = new TestSuite();
        ServiceLoader<Nd4jBackend> backends = ServiceLoader.load(Nd4jBackend.class);
        Iterator<Nd4jBackend> backendIterator = backends.iterator();
        Reflections reflections = new Reflections("org.nd4j");
        Set<Class<? extends BaseNd4jTest>> testClasses = reflections.getSubTypesOf(BaseNd4jTest.class);
        List<Nd4jBackend> nd4jBackends = new ArrayList<>();
        while(backendIterator.hasNext()) {
            nd4jBackends.add(backendIterator.next());
        }

        for(Class<? extends BaseNd4jTest> clazz : testClasses) {
            for(Nd4jBackend backend : nd4jBackends) {
                Constructor<BaseNd4jTest> constructor = (Constructor<BaseNd4jTest>) clazz.getConstructor(Nd4jBackend.class);
                Test test = constructor.newInstance(backend);
                testSuite.addTest(constructor.newInstance(backend));


            }
        }
        return testSuite;


    }

}
