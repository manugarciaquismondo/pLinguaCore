SET LOCALEPROJECT="pLinguaCore5.0"
SET LOCALEWORKSPACE=%WORKSPACE%
SET LOCALEPROJECTROUTE=%LOCALEWORKSPACE%\%LOCALEPROJECT%\
cd %LOCALEPROJECTROUTE%antlr
java -jar antlr-3.4-complete.jar %LOCALEPROJECTROUTE%org\gcn\plinguacore\parser\input\simplekernel\Kernel_Simulator_Lexer.g
java -jar antlr-3.4-complete.jar %LOCALEPROJECTROUTE%org\gcn\plinguacore\parser\input\simplekernel\Computation_Parser.g
java -jar antlr-3.4-complete.jar %LOCALEPROJECTROUTE%org\gcn\plinguacore\parser\input\simplekernel\Dictionary_Reader.g
pause