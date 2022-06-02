$(function() {
	$("#selectable").selectable();
	$("input[type=button],input[type=submit],button").button().click(
			function(event) {
				event.preventDefault();
			});

});

function adicionarCategoria() {
	var cboCategoria = document.getElementById("speed");
	var li = document.createElement("li");
	li.setAttribute('class', 'ui-widget-content');
	li.setAttribute('id',
			cboCategoria.options[cboCategoria.selectedIndex].value);
	var addli = document.getElementById("selectable").appendChild(li);
	var textlist = document
			.createTextNode(cboCategoria.options[cboCategoria.selectedIndex].value);
	addli.appendChild(textlist);

	cboCategoria.remove(cboCategoria.selectedIndex);
}

function ordenarCombo(obj) {
	var o = new Array();
	for ( var i = 0; i < obj.options.length; i++) {
		o[o.length] = new Option(obj.options[i].text, obj.options[i].value,
				obj.options[i].defaultSelected, obj.options[i].selected);
	}
	o = o.sort(function(a, b) {
		if ((a.text + "") < (b.text + "")) {
			return -1;
		}
		if ((a.text + "") > (b.text + "")) {
			return 1;
		}
		return 0;
	});

	for ( var i = 0; i < o.length; i++) {
		obj.options[i] = new Option(o[i].text, o[i].value,
				o[i].defaultSelected, o[i].selected);
	}
	// Codigo Fonte de ordenação:
	// http://codigofonte.uol.com.br/codigos/ordenar-options-de-uma-combo
}

function adicionarCategoriaNoComboBox(nome) {
	var opcao = document.createElement("option");
	document.getElementById("speed").options.add(opcao);
	opcao.text = nome;
	opcao.value = nome;
	ordenarCombo(document.getElementById("speed"));
}

function removerCategoria() {
	$('#selectable .ui-selected').each(function() {
		adicionarCategoriaNoComboBox($(this).attr('id'));
	});
	$('#selectable .ui-selected').remove();
}

function reclassificar() {
	var categoriasSelecionadas = [];
	var i = 0;
	$('#selectable li').each(function() {
		categoriasSelecionadas[i] = $(this).attr('id');
		i = i + 1;
	});

	var nomeReclassificacao = $('#nome').val();
	cadastrarReclassificacao(nomeReclassificacao, categoriasSelecionadas);
}

function atualizarGrauCategoria() {
	var lista = [];
	var j = 1;
	var i = 0;

	listaGrau = $('input[type=text][name=grau]').val();
	$('.id').each(function() {
		lista[i] = $(this).val();
		i = i + 2;
	});
	$('input[type=text][name=grau]').each(function() {
		lista[j] = $(this).val();
		j = j + 2;
	});

	window.location = "AtualizarGrauCategoriaController.do?lista=" + lista;

}

function cadastrarReclassificacao(nomeReclassificacao, categorias) {
	if (nomeReclassificacao.length > 0 && categorias.length > 0) {
		    $.ajax({
					url : "CadastrarReclassificacaoController.do",
					method : "POST",
					async : false,
					data : {
						nomeReclassificacao : nomeReclassificacao,
						categorias : JSON.stringify(categorias)
					},
					dataType : "json",
					success : function(retorno) {
						if (retorno.sucesso === true) {
							if (retorno.mensagem == null) {
								alert("Cadastro com sucesso.");
								window.location = "ListarReclassificacaoController.do";										
							} else {
								alert(retorno.mensagem);
							}
						} else {
							alert("Um erro inesperado ocorreu!");
							console.log(retorno.mensagem);
						}
					},
					error : function() {
						alert("erro!");
					}

				});
	} else {
		alert("Por favor, preencha corretamente os campos do formulário.");
	}

}

function excluirReclassificacao(id) {
    $.ajax({
		url : "ExcluirReclassificacaoController.do",
		method : "POST",
		async : false,
		data : {
			id: id
		},
		dataType : "json",
		success : function(retorno) {
			if (retorno.sucesso === true) {
			  alert("Excluido com sucesso.");
			  window.location = "ListarReclassificacaoController.do";										
				 
			} else {
				alert("Um erro inesperado ocorreu!");
				console.log(retorno.mensagem);
			}
		},
		error : function() {
			alert("erro!");
				}

			});
} 

function analisarArquivo() {
	var conteudo = $("input[name='conteudo']:checked").val();
	var continuar = $("input[name='continuar']:checked").val();
	
	$.ajax({
		url : "AnalisarArquivoController.do",
		method : "POST",
		data : {
			conteudo : conteudo,
			continuar : continuar
		},
		dataType : "json",
		success : function(retorno) {
			if (retorno.sucesso === true) {
				alert("Analise do arquivo finalizada com sucesso.");
			} else {
				alert("Ocorreu um erro ao analisar o arquivo.");
				console.log(retorno.mensagem);
			}
		},
		error : function() {
			alert("Erro ao Analisar Arquivo.");
		}

	});
		
}


function analisarBancoDados() {
	var status = $("input[name='status']:checked").val();
	var continuar = $("input[name='continuar']:checked").val();
	
	$.ajax({
		url : "AnalisarBancoDeDadosController.do",
		method : "POST",
		data : {
			status : status,
			continuar : continuar
		},
		dataType : "json",
		success : function(retorno) {
			if (retorno.sucesso === true) {
				alert("Analise do banco de dados finalizada com sucesso.");
			} else {
				alert("Ocorreu um erro ao analisar banco de dados.");
				console.log(retorno.mensagem);
			}
		},
		error : function() {
			alert("Erro ao analisar Banco de Dados");
		}

	});

}

function gerarURLLightListAtiva() {
	var nome = $('#nomePasta').val();
	var tipoLista = $("input[name='tipo']:checked").val();
	$.ajax({
		url : "GerarURLLightListAtivaController.do",
		method : "POST",
		data : {
			nomePasta : nome,
			tipo : tipoLista
		},
		dataType : "json",
		success : function(retorno) {
			if (retorno.sucesso === true) {
				alert("A URLLightList foi gerada na pasta: " + nome);
			} else {
				alert("Um erro inesperado ocorreu ao gerar URLLightList.");
				console.log(retorno.mensagem);
			}
		},
		error : function() {
			alert("Erro ao URLLightList.");
		}

	});
}

function gerarURLLightListInativa() {
	    $.ajax({
				url : "GerarURLLightListInativaController.do",
				method : "POST",
				dataType : "json",
				success : function(retorno) {
					if (retorno.sucesso === true) {
						alert("A URLLightList inativa foi gerada com sucesso.");
						nome.text = "";
					} else {
						alert("Um erro inesperado ocorreu ao gerar URLLightList inativa.");
						console.log(retorno.mensagem);
					}
				},
				error : function() {
					alert("Erro ao URLLightList.");
				}

			});
}

function downloadMD5() {
	var urlDownload = $('#urlMD5').val();
	if (urlDownload.length > 0 ) {
	   $.ajax({
				url : "DownloadMD5Controller.do",
				async : false,
				method : "POST",
				data : {
					urlMD5 : urlDownload
				},
				dataType : "json",
				success : function(retorno) {
					if (retorno.sucesso === true) {
						alert(retorno.mensagem);
					} else {
						alert("Um erro inesperado ocorreu!");
						console.log(retorno.mensagem);
					}
				},
				error : function() {
					alert("erro!");
				}

			});
	   } else {
		alert("Por favor, preencha corretamente os campos.");
	}
}

function downloadBlackList() {
	var urlBlackList = $('#url').val();
	var urlMD5 = $('#urlMD5').val();
	
	if (urlBlackList.length > 0 && urlMD5.length > 0 ) {
	$.ajax({
		url : "DownloadURLBlackListController.do",
		method : "POST",
		data : {
			urlMD5: urlMD5,
			urlBlack : urlBlackList
		},
		dataType : "json",
		success : function(retorno) {
			if (retorno.sucesso === true) {
				alert("Download realizado.");

			} else {
				alert("Um erro inesperado ocorreu!");
				console.log(retorno.mensagem);
			}
		},
		error : function() {
			alert("erro!");
		}

	});
	} else {
		alert("Por favor, preencha corretamente os campos.");
	}

}

function cadastrarDominio() {
	var nome = $('#nome').val();
	var categoria = $('#speed').val();

	if (nome.length > 0 && categoria.length > 0) {
		$.ajax({
			url : "CadastrarDominioController.do",
			method : "POST",
			async : false,
			data : {
				nome : nome,
				categoria : categoria
			},
			dataType : "json",
			success : function(retorno) {
				if (retorno.sucesso === true) {
					$('#nome').val('');
					if (retorno.mensagem == null) {
						alert("Cadastro com sucesso.");
					} else {
						alert(retorno.mensagem);
					}
				} else {
					alert("Um erro inesperado ocorreu!");
					console.log(retorno.mensagem);
				}
			},
			error : function() {
				alert("erro!");
			}

		});
	} else {
		alert("Por favor, preencha corretamente os campos do formulário.");
	}
}

function excluirDominio() {
	var id = $('#id').val();
	$.ajax({
		url : "ExcluirDominioController.do",
		method : "POST",
		async : false,
		data : {
			id : id
		},
		dataType : "json",
		success : function(retorno) {
			if (retorno.sucesso === true) {
				$('#nome').val('');
				if (retorno.mensagem == null) {
					alert("Domínio excluido com sucesso.");
				} else {
					alert(retorno.mensagem);
				}
			} else {
				alert("Um erro inesperado ocorreu!");
				console.log(retorno.mensagem);
			}
		},
		error : function() {
			alert("erro!");
		}

	});
}

function atualizarDominio() {
	var id = $('#id').val();
	var categoria = $('#speed').val();

	if (categoria.length > 0) {
		$.ajax({
			url : "AtualizarDominioController.do",
			method : "POST",
			async : false,
			data : {
				categoria : categoria,
				id : id
			},
			dataType : "json",
			success : function(retorno) {
				if (retorno.sucesso === true) {
				  alert("Atualizado com sucesso.");
			    }
				else {
					alert("Um erro inesperado ocorreu!");
					console.log(retorno.mensagem);
				}
			},
			error : function() {
				alert("erro!");
			}

		});
	} else {
		alert("Por favor, preencha corretamente os campos do formulário.");
	}
}

function cadastrarAdministrador() {

	var login = $('#login').val();
	var senha = $('#senha').val();
	var confirmacao = $('#confirmacao').val();

	if (login.length > 0 && senha.length > 0 && confirmacao.length > 0) {
		$.ajax({
			url : "CadastrarAdministradorController.do",
			method : "POST",
			async : false,
			data : {
				login : login,
				senha : senha,
				confirmacao : confirmacao
			},
			dataType : "json",
			success : function(retorno) {
				if (retorno.sucesso === true) {
					$('#login').val('');
					$('#senha').val('');
					$('#confirmacao').val('');
					if (retorno.mensagem == null) {
						alert("Cadastro com sucesso.");
					} else {
						alert(retorno.mensagem);
					}

				} else {
					alert("Um erro inesperado ocorreu!");
					console.log(retorno.mensagem);
				}
			},
			error : function() {
				alert("erro!");
			}

		});
	} else {
		alert("Por favor, preencha corretamente os campos do formulário.");
	}
}
function atualizarAdministrador() {
	var id = $('#id').val();
	var login = $('#login').val();
	var senha = $('#senha').val();
	var confirmacao = $('#confirmacao').val();
	
	if (login.length > 0 && senha.length > 0 && confirmacao.length > 0) {
		$.ajax({
			url : "AtualizarAdministradorController.do",
			method : "POST",
			async : false,
			data : {
				id : id,
				login : login,
				senha : senha,
				confirmacao : confirmacao
			},
			dataType : "json",
			success : function(retorno) {
				if (retorno.sucesso === true) {
					if (retorno.mensagem == null) {
						alert("Atualizado com sucesso.");
					} else {
						alert(retorno.mensagem);
					}

				} else {
					alert("Um erro inesperado ocorreu!");
					console.log(retorno.mensagem);
				}
			},
			error : function() {
				alert("erro!");
			}

		});
	} else {
		alert("Por favor, preencha corretamente os campos do formulário.");
	}
}


function logar() {
   var login = $('#login').val();
   var senha = $('#senha').val();
	
	if (login.length > 0 && senha.length) {
		$.ajax({
			url : "LoginController.do",
			method : "POST",
			async : false,
			data : {
				login : login,
				senha : senha
			},
			dataType : "json",
			success : function(retorno) {
				if (retorno.sucesso === true) {
					if (retorno.mensagem == null) {
						window.location = "index.jsp";
					} else {
						alert(retorno.mensagem);
					}

				} else {
					alert("Um erro inesperado ocorreu!");
					console.log(retorno.mensagem);
				}
			},
			error : function() {
				alert("erro!");
			}

		});
	} else {
		alert("Por favor, preencha corretamente os campos do formulário.");
	}
}

function exibirIndex() {
	window.location = "index.jsp";
}

