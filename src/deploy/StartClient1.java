package deploy;

import ir.pint.soltoon.soltoongame.client.ClientRunner;
import ir.pint.soltoon.utils.shared.comminucation.ComInputStream;
import ir.pint.soltoon.utils.shared.comminucation.ComOutputStream;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteConfig;
import student.payam.WisePlayer;

public class StartClient1 {
    public static void main(String[] args) {
//        ComInputStream.DEBUG = true;
//        ComOutputStream.DEBUG = true;

        ComRemoteConfig remote = new ComRemoteConfig("passa", 8586);
        ClientRunner.run(WisePlayer.class, remote);
    }
}
