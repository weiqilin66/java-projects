package org.wayne.source.mybatis;

// 很难很牛逼的结果处理器

public class Mybatis09 {
   /* public abstract class ResultHandler {
        protected static final boolean DEBUG = Config.getPluginDebug();

        public ResultHandler() {
        }

        public void waitForResult(ResultID var1, AppletID var2) throws IOException {
            int var3 = JVMManager.getManager().getJVMIDForApplet(var2);

            for(int var4 = var3; !LiveConnectSupport.resultAvailable(var1) && !JVMManager.getManager().instanceExited(var4) && !JVMManager.getManager().appletExited(var2) && var4 >= 0; var4 = JVMManager.getManager().getJVMIDForApplet(var2)) {
                this.waitForSignal();
            }

        }

        public void waitForResult(ResultID var1, int var2, AppletID var3) {
            for(int var4 = JVMManager.getManager().getJVMIDForApplet(var3); !LiveConnectSupport.resultAvailable(var1) && !JVMManager.getManager().instanceExited(var2) && !JVMManager.getManager().appletExited(var3) && var4 >= 0 && var2 == var4; var4 = JVMManager.getManager().getJVMIDForApplet(var3)) {
                this.waitForSignal();
            }

        }

        public abstract void waitForSignal();

        public abstract void waitForSignal(long var1);
    }*/
}
