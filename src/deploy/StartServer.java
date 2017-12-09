package deploy;

import ir.pint.soltoon.soltoongame.server.ServerRunner;
import ir.pint.soltoon.soltoongame.shared.GameConfiguration;
import ir.pint.soltoon.utils.shared.comminucation.ComInputStream;
import ir.pint.soltoon.utils.shared.comminucation.ComOutputStream;
import ir.pint.soltoon.utils.shared.comminucation.ComRemoteInfo;

public class StartServer {
    public static void main(String[] args) {
//        ComInputStream.DEBUG = true;
//        ComOutputStream.DEBUG = true;

        GameConfiguration.NUMBER_OF_PLAYERS = 1;

        ComRemoteInfo remoteInfo1 = new ComRemoteInfo("student/payam", "Payam Mohammadi", "127.0.0.1", 8586, "passa");
        ServerRunner.run(remoteInfo1);
    }
}
