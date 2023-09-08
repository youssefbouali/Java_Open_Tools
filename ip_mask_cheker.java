import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class IPMaskValidator {

    public static boolean isValidIP(String ipAddress) {
        String ipRegex = "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\." +
                "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

        Pattern pattern = Pattern.compile(ipRegex);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }

    public static boolean isValidSubnetMask(String subnetMask) {
        // Subnet mask should be a valid IP address and contain a contiguous sequence of '1's followed by '0's.
        if (!isValidIP(subnetMask)) {
            return false;
        }

        String[] octets = subnetMask.split("\\.");
        if (octets.length != 4) {
            return false;
        }

        // Convert each octet to its binary representation.
        StringBuilder binaryMask = new StringBuilder();
        for (String octet : octets) {
            int octetValue = Integer.parseInt(octet);
            String binaryOctet = Integer.toBinaryString(octetValue);

            // Ensure each binary octet is 8 characters long by adding leading zeros.
            while (binaryOctet.length() < 8) {
                binaryOctet = "0" + binaryOctet;
            }

            binaryMask.append(binaryOctet);
        }

        // Check if the binary mask contains a contiguous sequence of '1's followed by '0's.
        String binaryMaskStr = binaryMask.toString();
        int indexOfFirstZero = binaryMaskStr.indexOf('0');
        if (indexOfFirstZero == -1 || indexOfFirstZero == 0) {
            return false;
        }

        // Ensure all bits after the first zero are '0'.
        for (int i = indexOfFirstZero; i < binaryMaskStr.length(); i++) {
            if (binaryMaskStr.charAt(i) != '0') {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        //String ipAddress = "192.168.1.1";
        
        Scanner input = new Scanner(System.in);
        System.out.print("Enter your ipAddress: ");
        // takes input from the keyboard
        String ipAddress = input.nextLine();
        
        
        System.out.print("Enter your subnetMask: ");
        // takes input from the keyboard
        String subnetMask = input.nextLine();

        if (isValidIP(ipAddress) && isValidSubnetMask(subnetMask)) {
            System.out.println("Both IP address and subnet mask are valid.");
        } else {
            System.out.println("Either IP address or subnet mask is invalid.");
        }
    }
}
