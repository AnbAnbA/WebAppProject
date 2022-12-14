# WebAppProject
Это мобильное приложение предназначеннно для использования аптеками. Оно предоставляет возможность просмотра базы медикаментов. Их наименование, произоводителя, страну производителя и цену.
## Начало работы
Эти инструкции предоставят вам копию проекта и помогут запустить на вашем локальном компьютере для разработки и тестирования.
### Необходимые условия
Для установки ПО, в данном случае android studio, вам необходимо перейти на сайт и скачать последнюю версию приложения.
+ Переходим на сайт https://developer.android.com/studio
+ Нажимаем на Download Android Studio. 
  + Ждем пока файл загрузиться.
+ Производим установку.
  + Ждем пока подгрузятся все необходимые компоненты.
+ После можем начинать установку с github.

### Установка
Для установки проекта необходимое открыть android studio и склонировать проект с github.
```
 Открыываем android studio и выбираем Get from VCS.
 Далее копируем ссылку репозитория https://github.com/AnbAnbA/WebAppProject
 и вставляем её в поле URL, и выбираем папку, в которую нужно склонировать проект.
 И нажимаем кнопку Clone.
```
![log](https://media.geeksforgeeks.org/wp-content/uploads/20201103235115/Clone4.png)
 ***Важно, чтобы путь к проекту был полностью на английском языке!***
```
После появится окошко с вопросом можно ли доверять проекту. Нажимаем trust project.
Ждем полной загрузки проекта. Можем начинать работать!
```
![log](https://developer.tomtom.com/static/8f9535997746c4399c15966ea233d38b/78274/android_studio_trust_gradle_project.png)
<br/>
<br/>***Обращаем внимание, что данный блок кода позволяет взаимодействовать с апишкой.***
```java
 private class GetMed extends AsyncTask<Void, Void, String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL("https://ngknn.ru:5001/NGKNN/БыковаАА/api/Medicines");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();

            } catch (Exception exception) {
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            try
            {
                JSONArray tempArray = new JSONArray(s);
                for (int i = 0;i<tempArray.length();i++)
                {
                    JSONObject productJson = tempArray.getJSONObject(i);
                    Med tempProduct = new Med(
                            productJson.getInt("ID"),
                            productJson.getString("NameMed"),
                            productJson.getString("Manufacturers"),
                            productJson.getString("Manufacturer_country"),
                            productJson.getDouble("PriceMed"),
                            productJson.getString("Image")
                    );
                    listMed.add(tempProduct);
                    pAdapter.notifyDataSetInvalidated();
                }
            } catch (Exception ignored) {
            }
        }
    }
```


## Авторы
* **AnbAnbA** - [AnbAnbA](https://github.com/AnbAnbA)
