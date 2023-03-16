# Doctors
## _Applicazione per dottori di base_

Il numero massimo di pazienti associabili ad un dottore dipende dall'età media di questi:
- età media [0, 50]    -> max pazienti: 80
- età media [51, 60]   -> max pazienti: 70
- età media [61, 70]   -> max pazienti: 60
- età media oltre i 70 -> max pazienti: 50

Si presume che ogni paziente abbia un dispositivo per la misurazione della pressione.
Questo dispositivo comunica in maniera automatica le misurazioni sul database (condiviso tra app e dispositivo)

L'applicazione ha lo scopo di permette ai dottori:
- di gestire l'anagrafica dei propri pazienti
- di essere informati se i valori delle ultime tre misurazioni del device sono al di fuori degli intervalli valori indicati nella tabella

| ETA'     | SESSO | RANGE PRESSIONE |
|----------|-------|-----------------|
| [0, 50]  | F     | [60, 140]       |
| [51, 60] | F     | [70, 130]       |
| [61, 70] | F     | [85, 125]       |
| oltre 70 | F     | [80, 120]       |
| [0, 50]  | M     | [70, 130]       |
| [51, 60] | M     | [75, 125]       |
| [61, 70] | M     | [85, 120]       |
| oltre 70 | M     | [80, 120]       |

- se non c'è comunicazione per un paziente negli ultimi 5 gg (nessuna misurazione dal dispositivo) viene inviata una email al dottore associato al paziente
