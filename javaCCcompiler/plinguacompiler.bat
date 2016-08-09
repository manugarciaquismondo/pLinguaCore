SET LOCALEPROJECT="pLinguaCore5.0"
SET LOCALEWORKSPACE=%WORKSPACE%
cd %LOCALEWORKSPACE%\%LOCALEPROJECT%\org\gcn\plinguacore\parser\input\plingua\
%JAVACC%\javacc.bat PlinguaJavaCcParser.jj
cd %LOCALEWORKSPACE%\%LOCALEPROJECT%\javaCCcompiler
pause