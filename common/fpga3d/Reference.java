package fpga3d;

public class Reference
{
    public static final String MOD_CHANNEL = "FPGA3D";

    public static final String MOD_ID = "FPGA3D";
    public static final String MOD_NAME = "FPGA3D";
    public static final String VERSION_NUMBER = "0.1";

    public static final String SERVER_PROXY_CLASS = "fpga3d.core.proxy.CommonProxy";
    public static final String CLIENT_PROXY_CLASS = "fpga3d.core.proxy.ClientProxy";

    public static final class Strings
    {
        public static final String FPGA_NAME = "FPGA";
    }

    public static final class GuiIDs
    {
        public static final int FPGA = 0;
    }

    public static final class BlockIDs
    {
        public static final int FPGA_DEFAULT = 2540;
        public static int FPGA;
    }

    public static final class Textures
    {
        public static final String GUI_SHEET_LOCATION = "/mods/fpga3d/textures/gui/";

        public static final String GUI_FPGA = GUI_SHEET_LOCATION + "fpga.png";
    }

    public static final class MessageIDs
    {
        public static final int FPGA = 0;
    }

    public static final class Constants
    {
        public static final int NUM_ENDPOINTS = 25;
        public static final int LUT_SIZE = 8;
        public static final int FF_SIZE = 2;
    }
}
