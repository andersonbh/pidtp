# Trabalho Prático de Processamento de Imagens Digitais

Antes de iniciar o desenvolvimento, instale e configure as seguintes dependências:

1. [Node.js][]: O Node é utilizado para rodar um servidor de desenvolvimento e fazer o build do projeto.

   Dependendo do sistema que você utilizar, o nodejs pode vir em pacotes já pré instalados.

Depois de instalar o nodeJs, execute os comandos:

    npm install
    
    npm install -g grunt-cli bower yo generator-karma generator-angular generator-jhipster
    
    bower install

Inclua o diretorio node_modules nas exclusões de indexação do projeto

Configure o Tomcat no Intellij IDEA e inicie a aplicação. Caso queira rodar a mesma sem o Intellij IDEA, execute:

    ./gradlew
    
Para monitorar alterações no código execute

    grunt

Para gerenciar as dependências de CSS e JavaScript, execute `bower update` ou `bower install`.

# Colocando em produção

Para colocar o sistema de inventário em produção, execute:

    ./gradlew -Pprod clean bootRepackage

Esse comando irá concatenar o CSS e os JavaScript. Para verificar se está tudo certo e rodando, execute:

    java -jar build/libs/*.war --spring.profiles.active=prod

E então, acesso [http://localhost:8080](http://localhost:8080) no browser.

# Testando

Testes unitários são executados pelo [Karma][] e escritos por [Jasmine][]. Eles estão em `src/test/javascript` e podem ser executados com o comando:

    grunt test

[Node.js]: https://nodejs.org/
[Bower]: http://bower.io/
[Grunt]: http://gruntjs.com/
[BrowserSync]: http://www.browsersync.io/
[Karma]: http://karma-runner.github.io/
[Jasmine]: http://jasmine.github.io/2.0/introduction.html
[Protractor]: https://angular.github.io/protractor/