
                                          function validarDocumento() {
                                            var documento = document.getElementById("nDocumento").value;
                                        
                                            // Permitir solo números y limitar a 8 dígitos
                                            documento = documento.replace(/\D/g, ''); // Elimina cualquier carácter no numérico
                                        
                                            if (documento.length > 8) {
                                              documento = documento.slice(0, 8); // Limita a 8 dígitos
                                            }
                                        
                                            document.getElementById("nDocumento").value = documento; // Actualiza el valor del campo
                                          }
                        