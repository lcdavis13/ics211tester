    Mac OS X            	   2   �      �                                      ATTR       �   �   5                  �   *  $com.apple.metadata:_kMDItemUserTags     �     com.apple.provenance bplist00�                            	  ^��n����    Mac OS X            	   2   �      �                                      ATTR       �   �   5                  �   *  $com.apple.metadata:_kMDItemUserTags     �     com.apple.provenance bplist00�                            	  ^��n����## Getting Started

Welcome to the VS Code Java world. Here is a guideline to help you get started to write Java code in Visual Studio Code.

## Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

## Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).
    Mac OS X            	   2   q      �                                      ATTR       �   �                     �     com.apple.provenance   ^��n����    Mac OS X            	   2   �      �                                      ATTR       �   �   5                  �   *  $com.apple.metadata:_kMDItemUserTags     �     com.apple.provenance bplist00�                            	  ^��n����    Mac OS X            	   2   �      �                                      ATTR       �   �   5                  �   *  $com.apple.metadata:_kMDItemUserTags     �     com.apple.provenance bplist00�                            	  ^��n����    Mac OS X            	   2   �      �                                      ATTR       �   �   5                  �   *  $com.apple.metadata:_kMDItemUserTags     �     com.apple.provenance bplist00�                            	  ^��n��������   =
  Account  java/lang/Object  java/awt/event/ActionListener initialAmount !Ljavax/swing/JFormattedTextField; yearlyInterest monthlyPayment result Ljavax/swing/JTextArea; <init> ()V Code
    	    	   	 	   
 	     LineNumberTable LocalVariableTable this 	LAccount; computeOutputs (DDD)Ljava/lang/String;@Y       #     % & ' makeConcatWithConstants '(Ljava/lang/String;D)Ljava/lang/String;  % amount D interest payment interestVal 	returnVal Ljava/lang/String; balance i I StackMapTable 5 java/lang/String actionPerformed (Ljava/awt/event/ActionEvent;)V
 9 ; : javax/swing/JFormattedTextField < = getText ()Ljava/lang/String;
 ? A @ java/lang/Double B C parseDouble (Ljava/lang/String;)D	 E G F java/lang/System H I out Ljava/io/PrintStream; K button pressed.
 M O N java/io/PrintStream P Q println (Ljava/lang/String;)V  S & T (D)Ljava/lang/String;  S  S@(      
  Z  
 \ ^ ] javax/swing/JTextArea _ Q setText e Ljava/awt/event/ActionEvent; initialAmountDouble yearlyInterestDouble monthlyPaymentDouble createField #()Ljavax/swing/JFormattedTextField;
 h j i java/text/NumberFormat k l getNumberInstance ()Ljava/text/NumberFormat;
 h n o p setMinimumFractionDigits (I)V
 9 r  s (Ljava/text/Format;)V
 ? u v w valueOf (D)Ljava/lang/Double;
 9 y z { setValue (Ljava/lang/Object;)V
 9 } ~ p 
setColumns
 9 � � p setHorizontalAlignment � java/awt/Font � 
Monospaced
 � �  � (Ljava/lang/String;II)V
 9 � � � setFont (Ljava/awt/Font;)V amountFormat Ljava/text/NumberFormat; numericField I(Ljava/lang/String;Ljavax/swing/JFormattedTextField;)Ljavax/swing/JPanel; � javax/swing/JPanel
 �  � javax/swing/BoxLayout
 � �  � (Ljava/awt/Container;I)V
 � � � � 	setLayout (Ljava/awt/LayoutManager;)V
 � � � � add *(Ljava/awt/Component;)Ljava/awt/Component; � javax/swing/JLabel
 � �  Q
 � � label 
inputField panel Ljavax/swing/JPanel; jl Ljavax/swing/JLabel; createContents ()Ljavax/swing/JPanel;
  � e f �   initial amount 
  � � � �  yearly interest  �  monthly payment  � javax/swing/JButton � 	 Compute 
 � �
 � � � � addActionListener "(Ljava/awt/event/ActionListener;)V
 \ �
 \ �
 \ � � � setEditable (Z)V
 \ � � p setRows � javax/swing/JScrollPane
 � �  � (Ljava/awt/Component;II)V b Ljavax/swing/JButton; vertical 
horizontal 	spCurrent Ljavax/swing/JScrollPane; main ([Ljava/lang/String;)V � javax/swing/JFrame � account
 � �
 � � � p setDefaultCloseOperation
  
 � � � � getContentPane ()Ljava/awt/Container;
  � � �
 � � � java/awt/Container
 � � �  pack
 � � � � 
setVisible args [Ljava/lang/String; frame Ljavax/swing/JFrame; 
SourceFile Account.java BootstrapMethods
 � � � $java/lang/invoke/StringConcatFactory & � �(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite; � � 
 � initial amount:  yearly interest: % monthly payment:  InnerClasses %java/lang/invoke/MethodHandles$Lookup java/lang/invoke/MethodHandles Lookup !              	      
                  W     *� *� *� *� *� �              	                    
       �     B(  oc9":&� $  :&9	6� 	kc9		� (  :�����       & 	   
 	           *  5  ?     H    B ) *     B + *    B , *  	 9 - *   5 . /   ( 0 * 	  " 1 2  3    �    4    6 7     � 
    e*� � 8� >I*� � 8� >9*� � 8� >9� DJ� L� D(� R  � L� D� U  � L� D� V  � L*� ( Wo� Y� [�       & 	   *  +  , # . + / 7 0 D 1 Q 3 d 4    4    e       e ` a   Z b *   N c *  # B d *  
 e f     �     7� gK*� m� 9Y*� qL+� t� x+
� |+� +� �Y�� �� �+�       & 	   8  9 	 ;  :  <  =   > % ? 5 @       3 � �    % )   
 � �     �     <� �Y� �M,� �Y,� �� �,+� �W� �Y*� �N-� �Y�� �� �,-� �W,�       & 	   E  F  G  F  H  I $ J 4 K : L    *    < � /     < �    4 � �  $  � �   � �    p     �� �Y� �L*� �� *� �� *� �� +�*� � �� �W+�*� � �� �W+�*� � �� �W� �Y�� �M,*� �+,� �W*� \Y"2� �� *� � �Y�� �� �*� � �*� � �>6� �Y*� � �:+� �W+� �Y+� �� �+�       ^    R  S  T  U  V + W 9 X G Y Q Z V [ \ ] l ^  _ � ` � a � b � d � c � e � g � h � g � i    >    �      � � �  Q k � �  � * � 2  � & � 2  �  � �  	 � �     �     -� �Yٷ �L+� ܻ Y� �M+� �,� � �W+� �+� �           m 
 n  o  p # q ' r , t         - � �   
 # � �    �    �    � �      �  � �  � �  � �   �    
 	     Mac OS X            	   2   q      �                                      ATTR       �   �                     �     com.apple.provenance   ^��n����{
    "java.project.sourcePaths": ["src"],
    "java.project.outputPath": "bin",
    "java.project.referencedLibraries": [
        "lib/**/*.jar"
    ]
}
    Mac OS X            	   2   q      �                                      ATTR       �   �                     �     com.apple.provenance   ^��n����public class Account implements java.awt.event.ActionListener {

  javax.swing.JFormattedTextField initialAmount = null;
  javax.swing.JFormattedTextField yearlyInterest = null;
  javax.swing.JFormattedTextField monthlyPayment = null;
  javax.swing.JTextArea result = null;

  /* here interest is monthly, so 10% interest per year is 0.8333% per month */
  private static String computeOutputs(double amount, double interest, double payment) {
    double interestVal = (1+interest/100);
    String returnVal = "";
    returnVal = returnVal + amount + "\n";
    double balance = amount;
    for (int i=0; i<12; i++) {
        balance = (balance * (interestVal) + payment);
        returnVal = returnVal + balance + "\n";
    } 
    return returnVal;

    // TO DO: students fill in this code, this is just an example
    // this example corresponds to an initial amount of 100.00,
    // yearly interest of 12%, so monthly interest of 1%,
    // and a 10.00 payment each month.
    // Having more or fewer than two decimal digits is fine.

    /* return "100.00\n" +    // if the initial amount is negative, e.g. -100,
           "111.00\n" +    // -91
           "122.11\n" +    // -81.91
           "133.33\n" +    // -72.73
           "144.66\n" +    // -63.46
           "156.11\n" +    // -54.09
           "167.67\n" +    // -44.63
           "179.34\n" +    // -35.08
           "191.14\n" +    // -25.43
           "203.05\n" +    // -15.68
           "215.08\n" +    //  -5.84
           "227.24\n" +    //   4.10
           "239.51\n";     //  14.14 */
  }

  public void actionPerformed(java.awt.event.ActionEvent e) {
    double initialAmountDouble = Double.parseDouble(initialAmount.getText());
    double yearlyInterestDouble = Double.parseDouble(yearlyInterest.getText());
    double monthlyPaymentDouble = Double.parseDouble(monthlyPayment.getText());
    
    System.out.println("button pressed.");
    System.out.println("initial amount: " + initialAmountDouble);
    System.out.println("yearly interest: " + yearlyInterestDouble + "%");
    System.out.println("monthly payment: " + monthlyPaymentDouble);

    result.setText(computeOutputs(initialAmountDouble, (yearlyInterestDouble/12), monthlyPaymentDouble));
  }

  private static javax.swing.JFormattedTextField createField() {
    java.text.NumberFormat amountFormat;
    amountFormat = java.text.NumberFormat.getNumberInstance();
    amountFormat.setMinimumFractionDigits(2);
    javax.swing.JFormattedTextField amount =
       new javax.swing.JFormattedTextField(amountFormat);
    amount.setValue(0.0);
    amount.setColumns(10);
    amount.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
    amount.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
    return amount;
  }
    
  private static javax.swing.JPanel
      numericField(String label, javax.swing.JFormattedTextField inputField) {
    javax.swing.JPanel panel = new javax.swing.JPanel();
    panel.setLayout(new javax.swing.BoxLayout(panel,
                    javax.swing.BoxLayout.X_AXIS));
    panel.add(inputField);
    javax.swing.JLabel jl = new javax.swing.JLabel(label);
    jl.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
    panel.add(jl);
    return panel;
  }

  // code to add the contents of the frame
  private javax.swing.JPanel createContents() {
    /* the container for all the elements */
    javax.swing.JPanel panel = new javax.swing.JPanel();
    initialAmount = createField();
    yearlyInterest = createField();
    monthlyPayment = createField();
    panel.add(numericField("  initial amount ", initialAmount));
    panel.add(numericField(" yearly interest ", yearlyInterest));
    panel.add(numericField(" monthly payment ", monthlyPayment));
    javax.swing.JButton b = new javax.swing.JButton(" Compute ");
    b.addActionListener(this);
    panel.add(b);
    /* a space for the output */
    result = new javax.swing.JTextArea("", 5, 50);
    result.setFont(new java.awt.Font("Monospaced", java.awt.Font.PLAIN, 14));
    result.setEditable(false);
    result.setRows(5);
    int vertical = javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS;
    int horizontal = javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER;
    javax.swing.JScrollPane spCurrent =
      new javax.swing.JScrollPane(result, vertical, horizontal);
    panel.add(spCurrent);

    panel.setLayout(new javax.swing.BoxLayout(panel,
                    javax.swing.BoxLayout.Y_AXIS));
    return panel;
  }

  public static void main(String[] args) {
    javax.swing.JFrame frame = new javax.swing.JFrame("account");
    frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
    Account account = new Account();
    frame.getContentPane().add(account.createContents());
    frame.pack();   // Size the frame.
    frame.setVisible(true); // Show the frame.

  }
}    Mac OS X            	   2   q      �                                      ATTR       �   �                     �     com.apple.provenance   ^��n����