@docstring @passed @consistent
Feature: Tests with docstrings

  Scenario: Sample
    When I use the following text:
      """
      Some multiline $ £
      docstring text
      """
    Then I should see the following text:
      """
      Another multiline
      docstring output
    ₠	8352	20A0	 	EURO-CURRENCY SIGN
	₡	8353	20A1	 	COLON SIGN
	₢	8354	20A2	 	CRUZEIRO SIGN
	₣	8355	20A3	 	FRENCH FRANC SIGN
	₤	8356	20A4	 	LIRA SIGN
	₥	8357	20A5	 	MILL SIGN
	₦	8358	20A6	 	NAIRA SIGN
	₧	8359	20A7	 	PESETA SIGN
	₨	8360	20A8	 	RUPEE SIGN
	₩	8361	20A9	 	WON SIGN
	₪	8362	20AA	 	NEW SHEQEL SIGN
	₫	8363	20AB	 	DONG SIGN
	€	8364	20AC	&euro;	EURO SIGN
	₭	8365	20AD	 	KIP SIGN
	₮	8366	20AE	 	TUGRIK SIGN
	₯	8367	20AF	 	DRACHMA SIGN
	₰	8368	20B0	 	GERMAN PENNY SYMBOL
	₱	8369	20B1	 	PESO SIGN
	₲	8370	20B2	 	GUARANI SIGN
	₳	8371	20B3	 	AUSTRAL SIGN
	₴	8372	20B4	 	HRYVNIA SIGN
	₵	8373	20B5	 	CEDI SIGN
	₶	8374	20B6	 	LIVRE TOURNOIS SIGN
	₷	8375	20B7	 	SPESMILO SIGN
	₸	8376	20B8	 	TENGE SIGN
	₹	8377	20B9	 	INDIAN RUPEE SIGN
      """

