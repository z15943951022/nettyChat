package szzii.com.MyStream.part2;

import java.util.function.Function;

/**
 * @author szz
 */
public class NextItemEvalProcess {

    private EvalFunction evalFunction;

    public NextItemEvalProcess(EvalFunction evalFunction) {
        this.evalFunction = evalFunction;
    }

    MyStream eval(){
        return evalFunction.apply();
    }
}


