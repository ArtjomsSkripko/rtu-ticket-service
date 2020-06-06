Feature: Ticket creation

  Scenario: Create intercity tickets
    Given 1 journey offer from RIGA to DAUGAVPILS
    And add intercity offer to 1 journey wrapper
    When call create tickets
    Then response status is OK
    And response has 1 ticket
    And 1 ticket has route from RIGA to DAUGAVPILS

  Scenario: Create regional tickets
    Given 1 journey offer from RIGA to RIGA
    And add RIGA regional offer to 1 journey wrapper
    When call create tickets
    Then response status is OK
    And response has 1 ticket
    And 1 ticket has route from RIGA to RIGA

  Scenario: Create mixed tickets
    Given 1 journey offer from RIGA to DAUGAVPILS
      And add RIGA regional offer to 1 journey wrapper
      And add DAUGAVPILS regional offer to 1 journey wrapper
      And add intercity offer to 1 journey wrapper
    When call create tickets
    Then response status is OK
      And response has 3 tickets
      And 1 ticket has route from RIGA to RIGA
      And 2 ticket has route from DAUGAVPILS to DAUGAVPILS
      And 3 ticket has route from RIGA to DAUGAVPILS