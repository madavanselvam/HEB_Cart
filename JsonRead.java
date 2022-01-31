package HEBshoppingcart.HEBshoppingcart;

import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonRead {

	public static void main(String[] args) {

        JSONParser jsonParser = new JSONParser();

        try {


        	Double subtotal = 0.0;
            Double subtotalafterdiscount = 0.0;
            Double subtotaloftaxableitems = 0.0;
            Double subtotaloftaxableitemsafterdiscount = 0.0;

            Double discountprice ;
            Double discountedprice ;
            Double discountedtotal = 0.0;

            Double tax ;
            Double recalculatedtax ;
            Double taxafterdiscount ;
            Double recalculatedtaxafterdiscount ;

            Double grandtotal ;
            Double grandtotalafterdiscount ;
            Double recalculatedgrandtotal ;
            Double recalculatedgrandtotalafterdiscount ;
            DecimalFormat df = new DecimalFormat("####0.00");

            Object cartobj = jsonParser.parse(new FileReader("jsonfiles/Cart.json"));

            JSONArray cartdetails = (JSONArray) cartobj;

            for (Object c : cartdetails) {
                JSONObject cart = (JSONObject) c;

                
                Long sku = (Long) cart.get("sku");
                
                System.out.println("sku:" + sku);

                String itemName = (String) cart.get("itemName");
                System.out.println("itemName::::" + itemName);


                Double price = (Double) cart.get("price");
                System.out.println("price::::" + price);

                Boolean isTaxable = (Boolean) cart.get("isTaxable");
                System.out.println("isTaxable::::" + isTaxable);

                discountprice = discountsku(sku);
                System.out.println("discountprice::::" + df.format(discountprice));
                discountedprice = (Double) cart.get("price") - discountprice;
                System.out.println("discountedprice::::" + df.format(discountedprice));
                discountedtotal += discountprice;

                subtotal += (Double) cart.get("price");
                subtotalafterdiscount += (Double) cart.get("price") - discountprice;

                if(isTaxable.equals(true)) {
                    subtotaloftaxableitems += (Double) cart.get("price");
                    subtotaloftaxableitemsafterdiscount += (Double) cart.get("price") - discountprice;
                }

            }

            tax = subtotal * 0.0825;
            recalculatedtax = subtotaloftaxableitems * 0.0825;
            taxafterdiscount = subtotalafterdiscount * 0.0825;
            recalculatedtaxafterdiscount = subtotaloftaxableitemsafterdiscount * 0.0825;

            grandtotal = subtotal + tax;
            recalculatedgrandtotal = subtotal + recalculatedtax;
            grandtotalafterdiscount = subtotal + taxafterdiscount;
            recalculatedgrandtotalafterdiscount = subtotal + recalculatedtaxafterdiscount;

            System.out.println("\n");
            System.out.println("GrandTotal::::" + df.format(grandtotal));
            System.out.println("\n");
            System.out.println("Subtotal::::" + df.format(subtotal));
            System.out.println("Tax Total::::" + df.format(tax));
            System.out.println("GrandTotal::::" + df.format(grandtotal));
            System.out.println("\n");
            System.out.println("Subtotal::::" + df.format(subtotal));
            System.out.println("Recalculated Tax Total::::" + df.format(recalculatedtax));
            System.out.println("Recalculated GrandTotal::::" + df.format(recalculatedgrandtotal));
            System.out.println("\n");
            System.out.println("Subtotal::::" + df.format(subtotal));
            System.out.println("Total Discount::::" + df.format(discountedtotal));
            System.out.println("Subtotal Afterdiscount::::" + df.format(subtotalafterdiscount));
            System.out.println("Tax total after discount::::" + df.format(taxafterdiscount));
            System.out.println("GrandTotal after discount::::" + df.format(grandtotalafterdiscount));
            System.out.println("\n");
            System.out.println("Subtotal::::" + df.format(subtotal));
            System.out.println("Recalculated tax total after discount::::" + df.format(recalculatedtaxafterdiscount));
            System.out.println("Recalculated GrandTotal after discount::::" + df.format(recalculatedgrandtotalafterdiscount));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static double discountsku(Long sku) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        Object discountobj = jsonParser.parse(new FileReader("jsonfiles/discount.json"));

        JSONArray discountdetails = (JSONArray) discountobj;

        double discountprice = 0.0;

        for (Object d : discountdetails) {
            JSONObject discount = (JSONObject) d;
            Long discountsku = (Long) discount.get("appliedSku");

            if (sku.equals(discountsku)) {

                discountprice = (Double) discount.get("discountPrice");

            }
        }
        return discountprice;

    }
}
