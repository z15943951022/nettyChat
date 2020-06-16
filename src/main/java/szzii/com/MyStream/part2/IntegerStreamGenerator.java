package szzii.com.MyStream.part2;

import com.sun.org.apache.regexp.internal.RE;

/**
 * @author szz
 */
public class IntegerStreamGenerator {

    public static MyStream<Integer> getIntegerStream(int low , int high){
        return getIntegerStreamInner(low,high,true);
    }

    private static MyStream<Integer> getIntegerStreamInner(int low,int high,boolean isStart){
        if (low > high){
            return Stream.makeEmptyStream();
        }
        if (isStart){
            return new MyStream.Builder<Integer>()
                    .nextItemEvalProcess(new NextItemEvalProcess(()-> getIntegerStreamInner(low,high,false))).build();
        }else {
            return new MyStream.Builder<Integer>()
                    .head(low)
                    .nextItemEvalProcess(new NextItemEvalProcess(()->getIntegerStreamInner(low+1,high,false)))
                    .build();
        }
    }

}
