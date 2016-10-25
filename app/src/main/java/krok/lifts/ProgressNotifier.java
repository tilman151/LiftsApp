package krok.lifts;

/**
 * Created by tilman on 20.10.16.
 */
public interface ProgressNotifier {

    public void onBind(int maxProgress);

    public void onUpdate(int progress);

    public void  onFinish();

}
