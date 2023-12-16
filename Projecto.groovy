package intellica.consultoria.sistema.negocio.comercial

import intellica.consultoria.sistema.negocio.documentos.Documento
import intellica.consultoria.sistema.negocio.visto.Instituicao
import intellica.consultoria.sistema.parametrizacao.Estado
import intellica.consultoria.sistema.parametrizacao.Template
import intellica.consultoria.sistema.seguranca.SecUser
import intellica.consultoria.sistema.seguranca.auditoria.BaseEntity

class Projectos extends BaseEntity{

   
    String nota
    Date dataLimite
    String estado
    SecUser responsavel
    double precoTotal
    double nivelExecucao
    /*Contratos contratos*/
    Oportunidade oportunidade
    Date dataInicio
    Date dataIniPlanificado
    Date dataFimPlanif

    static final String ESTADO_CRIADO = 'Iniciado'

    static belongsTo = [cliente:Instituicao]
    static hasMany = [ficheiro: Documento, fases:Fase, recursoProjecto:RecursoProjecto, secUser:SecUser, pressuposto:Pressuposto, contratos: Contratos, requisicao:Requisicao]

    static constraints = {
        estado nullable: true, required: false
        codigo nullable: true, required: false
        nome nullable: false, required: true
        descricao nullable: true
        custo defaultValue: 0, required: false
        valorAdjudicacao defaultValue: 0, required: false
        nivelExecucao defaultValue: 0, required: false
        cliente nullable: true
        contratos nullable: true
        actividades nullable: true
        nota nullable: true
        ficheiro nullable: true
        recursoProjecto nullable: true
        fases nullable: true
        oportunidade nullable: true
        dataLimite nullable: true
        dataInicio nullable: true
        dataIniPlanificado nullable: true
        dataFimPlanificado nullable: true
        secUser nullable: true
        pressuposto nullable: true
        areaProjecto nullable: true
        tipoServico nullable: true
        requisicao nullable: true
    }

    static mapping = {
        nota type: 'text', sqlType: 'text'

    }

    String toString(){

        return this?.nome
    }

    String getReferencia()
    {
        return this.codigo
    }

    def beforeInsert()
    {
        super.beforeInsert()
        this?.setEstado(Projectos.ESTADO_CRIADO)
    }

    def shellService

    def afterInsert()
    {
        super.afterInsert()
        Contratos c = new Contratos()
        c.setNome(this?.nome)
        c.setCodigo(this?.codigo)
        c.setDataInicio(this?.dataInicio)
        c.setDataFim(this?.dataLimite)
        c.setValor(this?.valorAdjudicacao)
        c.setDescricao(this?.descricao)
        c.setObjecto(this?.nome)
        c.setTipo(Contratos.TIPO_CONTRATO)
        c.setProjectos(this)
        c.save(failOnError: true)

        /*Template template = Template.findByCodigo('PHCINTEG')
        Binding binding = new Binding()
        binding?.setVariable("object", this)
        println(template)
        GroovyShell shell = new GroovyShell(binding)
        def shellResult = shellService.run(template, binding)
        println(shellResult)*/
    }
}