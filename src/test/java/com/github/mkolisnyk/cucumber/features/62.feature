@passed
Feature: Login

  Background: 
    Given The app is clear
    When The wi-fi status is true and data status is true
    Then I see the Litigando logo
    And I see the ".activity.LoginActivity" activity in the page "Login"

  Scenario: SLogin_001-No conection to internet
    Given The wi-fi status is false and data status is false
    When I press on the Acceder button
    Then I must see a alert that has a title "Verifique su conexiÃ³n a Internet" and the message "Debe tener una conexiÃ³n a Internet para Iniciar SesiÃ³n"

  Scenario Outline: SLogin_-The alert to select an account only displayed if device has conection to internet
    Given The wi-fi status is and data status is
    When I press on the Acceder button
    Then I must see a dialog that has a title ""

    Examples: 
      | num | wifiStatus | dataStatus | titleAlert                       |
      | 002 | true       | false      | Elegir una cuenta para Litimovil |
      #|003| false      | true       | Elegir una cuenta para Litimovil |
      | 003 | true       | true       | Elegir una cuenta para Litimovil |

  Scenario: SLogin_004-Login invalid Account
    Given I press on the Acceder button
    When I select a different account of "@ema.com"
    Then I must see a alert that has a title "Cuenta seleccionada no es vÃ¡lida" and the message "Debe seleccionar una cuenta de "
    And I press back button device
    And I see the"LoginActivity" activity in the page "Login"

  Scenario: SLogin_005-Login Succesful Dependiente
    Given I press on the Acceder button
    When I select an account of "email"
    Then I must see the "Mis despachos" view
    And I see the label "Seleccione un despacho"

  Scenario: SLogin_006-Login Succesful Recorredor
    Given I press on the Acceder button
    When I select an account of "email"
    Then I must see the "Mis despachos" view
    And I see the label "Seleccione un despacho"
