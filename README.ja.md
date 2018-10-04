# Jsonlenium
[![CircleCI](https://circleci.com/gh/mapserver2007/Jsonlenium/tree/master.svg?style=svg)](https://circleci.com/gh/mapserver2007/Jsonlenium/tree/master)
[![Test Coverage](https://api.codeclimate.com/v1/badges/fa780bc872652811102c/test_coverage)](https://codeclimate.com/github/mapserver2007/Jsonlenium/test_coverage)  
JsonleniumはJSONファイルを使ってE2Eテストを行うツールです。

## 準備
実行にはJavaが必要(Java8以降)ですのであらかじめインストールしておいてください。

## 実行方法
```
$> git clone https://github.com/mapserver2007/Jsonlenium.git
$> cd Jsonlenium
$> .\gradlew uiTest -Ddriver=phantomjs -Dpath=C:\jsonlenium_test_files\test.json
$> .\gradlew uiParallelTest -Ddriver=phantomjs -Dpath=C:\jsonlenium_test_files\test.json -Ddivision=5
```

## タスク一覧
Jsonleniumは`タスク`が定義されており、タスク単位で実行します。<br>
使用可能なタスクは以下のとおりです。

| タスク名       | 内容                                                                                                                     |
|----------------|--------------------------------------------------------------------------------------------------------------------------|
| uiTest         | テストを実行する                                                                                                         |
| uiParallelTest | テストを並列で実行する<br>uiTestに比べて数倍速いが、かかる負荷も高い                                                     |
| uiTestCompile  | JSONファイルをコンパイルする<br>uiTest, uiParallelTestタスクで暗黙的に使われているタスクのため利用者が使用することはない |
| cleanAll       | コンパイルしたファイルを削除する<br>JSONファイルを更新した場合実行する必要がある                                         |
| unitTest       | Jsonleniumのユニットテストを実行する                                                                                     |
| coverageTest   | Jsonleniumのユニットテストを実行しカバレッジ測定を行う                                                                   |

## 指定可能パラメータ
Jsonleniumでのテスト実行時に指定可能なパラメータは以下のとおりです。

| パラメータ名 | 内容                                                                                                                                                                               | 指定例                  | 必須 |
|--------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------|------|
| -Ddriver     | 実行に使用するドライバ(WebDriver)を指定する<br>phantomjs/chrome/headlesschromeが指定可能                                                                                           | -Ddriver=headlesschrome | ◯    |
| -Dpath       | JSONテストファイルのパスを指定<br>絶対パスまたは相対パスが指定可能 ファイルまたはディレクトリ指定が可能<br>ディレクトリ指定の場合配下のJSONファイルすべてをを取得する              | -Dpath=C:\test.json     | ◯    |
| -Ddivision   | テストを並列実行する際の1プロセスに割り当てるための分割数<br>テスト総数が多くなるほどdivisionの値を増やすと速くなる<br>uiParallelTestタスク時に指定する<br>指定無しの場合は5が設定 | -Ddivision=5            | -    |
| -Dtimeout    | 実行中のテストの最大応答時間(秒)<br>timeout時間より応答が長くなる場合はNGとして終了する<br>指定無しの場合は最大応答時間は無制限                                                    | -Dtimeout=300           | -    |
| -i           | テストが成功した場合のログも標準出力する                                                                                                                                           | -i                      | -    |
| -d           | デバッグ情報のログも標準出力する                                                                                                                                                   | -d                      | -    |

## License
Licensed under the MIT
http://www.opensource.org/licenses/mit-license.php
