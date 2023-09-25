window.onload = function() {
  //<editor-fold desc="Changeable Configuration Block">

  // the following lines will be replaced by docker/configurator, when it runs in a docker-container
  window.ui = SwaggerUIBundle({
    spec: {
      "openapi": "3.0.1",
      "info": {
        "title": "Ke-Words",
        "description": "이 파일은 정적 파일로 test가 불가능하고, 실제 서버 상황과 다를 수 있습니다.",
        "version": "v1.0.0"
      },
      "servers": [
        {
          "url": "http://ke-words.kro.kr",
          "description": "Generated server url"
        }
      ],
      "paths": {
        "/api/token/reissue": {
          "post": {
            "tags": [
              "refresh-token-controller"
            ],
            "operationId": "reIssueAccessToken",
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "$ref": "#/components/schemas/RefreshTokenRequestDTO"
                  }
                }
              },
              "required": true
            },
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/JwtToken"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/quiz/word/meaning/test": {
          "post": {
            "tags": [
              "word-quiz-controller"
            ],
            "operationId": "test",
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/WordQuizTestProblemsResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/quiz/word/grade/{quizId}": {
          "post": {
            "tags": [
              "word-quiz-controller"
            ],
            "operationId": "gradingQuizTest",
            "parameters": [
              {
                "name": "quizId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "$ref": "#/components/schemas/GradeWordQuizTestRequestDTO"
                  }
                }
              },
              "required": true
            },
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/WordQuizProblemsResultResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/quiz/sentence/ordering/test": {
          "post": {
            "tags": [
              "sentence-quiz-controller"
            ],
            "operationId": "generateOrderingTest",
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/SentenceQuizOrderingTestProblemsResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/quiz/sentence/meaning/test": {
          "post": {
            "tags": [
              "sentence-quiz-controller"
            ],
            "operationId": "generateMeaningQuizTest",
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/SentenceQuizMeaningTestProblemsResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/quiz/sentence/grade/{quizId}": {
          "post": {
            "tags": [
              "sentence-quiz-controller"
            ],
            "operationId": "gradeQuiz",
            "parameters": [
              {
                "name": "quizId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "$ref": "#/components/schemas/GradeSentenceQuizTestRequestDTO"
                  }
                }
              },
              "required": true
            },
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/SentenceQuizProblemsResultResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/quiz/sentence/filling/test": {
          "post": {
            "tags": [
              "sentence-quiz-controller"
            ],
            "operationId": "generateFillingTest",
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/SentenceQuizFillingTestProblemsResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/member/register": {
          "post": {
            "tags": [
              "member-controller"
            ],
            "operationId": "register",
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "$ref": "#/components/schemas/MemberRequestDTO"
                  }
                }
              },
              "required": true
            },
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          }
        },
        "/api/member/logout": {
          "post": {
            "tags": [
              "member-controller"
            ],
            "operationId": "logout",
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          }
        },
        "/api/last/word/{wordId}/update": {
          "post": {
            "tags": [
              "last-word-controller"
            ],
            "operationId": "saveOrUpdate",
            "parameters": [
              {
                "name": "wordId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          }
        },
        "/api/last/sentence/{sentenceId}/update": {
          "post": {
            "tags": [
              "last-sentence-controller"
            ],
            "operationId": "saveOrUpdate_1",
            "parameters": [
              {
                "name": "sentenceId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          }
        },
        "/api/bookmark/word/{wordId}": {
          "post": {
            "tags": [
              "bookmark-word-controller"
            ],
            "operationId": "saveBookmarkWord",
            "parameters": [
              {
                "name": "wordId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          },
          "delete": {
            "tags": [
              "bookmark-word-controller"
            ],
            "operationId": "deleteBookmarkWord",
            "parameters": [
              {
                "name": "wordId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          }
        },
        "/api/bookmark/sentence/{sentenceId}": {
          "post": {
            "tags": [
              "bookmark-sentence-controller"
            ],
            "operationId": "saveBookmarkSentence",
            "parameters": [
              {
                "name": "sentenceId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          },
          "delete": {
            "tags": [
              "bookmark-sentence-controller"
            ],
            "operationId": "deleteBookmarkSentence",
            "parameters": [
              {
                "name": "sentenceId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          }
        },
        "/api/member/password": {
          "patch": {
            "tags": [
              "member-controller"
            ],
            "operationId": "changePassword",
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "$ref": "#/components/schemas/MemberChangePasswordRequestDTO"
                  }
                }
              },
              "required": true
            },
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          }
        },
        "/api/word": {
          "get": {
            "tags": [
              "word-controller"
            ],
            "operationId": "findAll",
            "parameters": [
              {
                "name": "pageable",
                "in": "query",
                "required": true,
                "style": "form",
                "explode": true,
                "schema": {
                  "$ref": "#/components/schemas/Pageable"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "$ref": "#/components/schemas/WordResponseDTO"
                      }
                    }
                  }
                }
              }
            }
          }
        },
        "/api/word/{wordId}": {
          "get": {
            "tags": [
              "word-controller"
            ],
            "operationId": "findById",
            "parameters": [
              {
                "name": "wordId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/WordResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/word/random": {
          "get": {
            "tags": [
              "word-controller"
            ],
            "operationId": "getWordsByRandom",
            "parameters": [
              {
                "name": "count",
                "in": "query",
                "required": true,
                "style": "form",
                "explode": true,
                "schema": {
                  "type": "integer",
                  "format": "int32"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "$ref": "#/components/schemas/WordResponseDTO"
                      }
                    }
                  }
                }
              }
            }
          }
        },
        "/api/sentence": {
          "get": {
            "tags": [
              "sentence-controller"
            ],
            "operationId": "findAll_1",
            "parameters": [
              {
                "name": "pageable",
                "in": "query",
                "required": true,
                "style": "form",
                "explode": true,
                "schema": {
                  "$ref": "#/components/schemas/Pageable"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "$ref": "#/components/schemas/SentenceResponseDTO"
                      }
                    }
                  }
                }
              }
            }
          }
        },
        "/api/sentence/{sentenceId}": {
          "get": {
            "tags": [
              "sentence-controller"
            ],
            "operationId": "findById_1",
            "parameters": [
              {
                "name": "sentenceId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/SentenceResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/sentence/random": {
          "get": {
            "tags": [
              "sentence-controller"
            ],
            "operationId": "getSentencesByRandom",
            "parameters": [
              {
                "name": "count",
                "in": "query",
                "required": true,
                "style": "form",
                "explode": true,
                "schema": {
                  "type": "integer",
                  "format": "int32"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "$ref": "#/components/schemas/SentenceResponseDTO"
                      }
                    }
                  }
                }
              }
            }
          }
        },
        "/api/ranking/word": {
          "get": {
            "tags": [
              "ranking-controller"
            ],
            "operationId": "getWordRankingList",
            "parameters": [
              {
                "name": "pageable",
                "in": "query",
                "required": true,
                "style": "form",
                "explode": true,
                "schema": {
                  "$ref": "#/components/schemas/Pageable"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "$ref": "#/components/schemas/RankingResponseDTO"
                      }
                    }
                  }
                }
              }
            }
          }
        },
        "/api/ranking/word/self": {
          "get": {
            "tags": [
              "ranking-controller"
            ],
            "operationId": "getMyWordRanking",
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/MyRankingResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/ranking/sentence": {
          "get": {
            "tags": [
              "ranking-controller"
            ],
            "operationId": "getSentenceRankingList",
            "parameters": [
              {
                "name": "pageable",
                "in": "query",
                "required": true,
                "style": "form",
                "explode": true,
                "schema": {
                  "$ref": "#/components/schemas/Pageable"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "$ref": "#/components/schemas/RankingResponseDTO"
                      }
                    }
                  }
                }
              }
            }
          }
        },
        "/api/ranking/sentence/self": {
          "get": {
            "tags": [
              "ranking-controller"
            ],
            "operationId": "getMySentenceRanking",
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/MyRankingResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/quiz/word": {
          "get": {
            "tags": [
              "word-quiz-controller"
            ],
            "operationId": "findAllByUserId",
            "parameters": [
              {
                "name": "pageable",
                "in": "query",
                "required": true,
                "style": "form",
                "explode": true,
                "schema": {
                  "$ref": "#/components/schemas/Pageable"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "$ref": "#/components/schemas/WordQuizProblemsResultForAllResponseDTO"
                      }
                    }
                  }
                }
              }
            }
          }
        },
        "/api/quiz/word/{quizId}": {
          "get": {
            "tags": [
              "word-quiz-controller"
            ],
            "operationId": "findById_2",
            "parameters": [
              {
                "name": "quizId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/WordQuizProblemsResultResponseDTO"
                    }
                  }
                }
              }
            }
          },
          "delete": {
            "tags": [
              "word-quiz-controller"
            ],
            "operationId": "deleteQuizResult",
            "parameters": [
              {
                "name": "quizId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          }
        },
        "/api/quiz/word/meaning/practice": {
          "get": {
            "tags": [
              "word-quiz-controller"
            ],
            "operationId": "practice",
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/WordQuizPracticeProblemsResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/quiz/sentence": {
          "get": {
            "tags": [
              "sentence-quiz-controller"
            ],
            "operationId": "findAllByUserId_1",
            "parameters": [
              {
                "name": "pageable",
                "in": "query",
                "required": true,
                "style": "form",
                "explode": true,
                "schema": {
                  "$ref": "#/components/schemas/Pageable"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "$ref": "#/components/schemas/SentenceQuizProblemsResultForAllResponseDTO"
                      }
                    }
                  }
                }
              }
            }
          }
        },
        "/api/quiz/sentence/{quizId}": {
          "get": {
            "tags": [
              "sentence-quiz-controller"
            ],
            "operationId": "findById_3",
            "parameters": [
              {
                "name": "quizId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/SentenceQuizProblemsResultResponseDTO"
                    }
                  }
                }
              }
            }
          },
          "delete": {
            "tags": [
              "sentence-quiz-controller"
            ],
            "operationId": "deleteQuizResult_1",
            "parameters": [
              {
                "name": "quizId",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "integer",
                  "format": "int64"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          }
        },
        "/api/quiz/sentence/ordering/practice": {
          "get": {
            "tags": [
              "sentence-quiz-controller"
            ],
            "operationId": "generateOrderingPractice",
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/SentenceQuizOrderingPracticeProblemsResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/quiz/sentence/meaning/practice": {
          "get": {
            "tags": [
              "sentence-quiz-controller"
            ],
            "operationId": "generateMeaningPractice",
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/SentenceQuizMeaningPracticeProblemsResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/quiz/sentence/filling/practice": {
          "get": {
            "tags": [
              "sentence-quiz-controller"
            ],
            "operationId": "generateFillingPractice",
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/SentenceQuizFillingPracticeProblemsResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/member/{email}/check": {
          "get": {
            "tags": [
              "member-controller"
            ],
            "operationId": "userEmailDuplicatedCheck",
            "parameters": [
              {
                "name": "email",
                "in": "path",
                "required": true,
                "style": "simple",
                "explode": false,
                "schema": {
                  "type": "string"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          }
        },
        "/api/member/info": {
          "get": {
            "tags": [
              "member-controller"
            ],
            "operationId": "getMemberInform",
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/MemberInfoResponseDTO"
                    }
                  }
                }
              }
            }
          }
        },
        "/api/last/word": {
          "get": {
            "tags": [
              "last-word-controller"
            ],
            "operationId": "findByUserId",
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/LastWordResponseDTO"
                    }
                  }
                }
              }
            }
          },
          "delete": {
            "tags": [
              "last-word-controller"
            ],
            "operationId": "delete",
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          }
        },
        "/api/last/sentence": {
          "get": {
            "tags": [
              "last-sentence-controller"
            ],
            "operationId": "findByUserId_1",
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "$ref": "#/components/schemas/LastSentenceResponseDTO"
                    }
                  }
                }
              }
            }
          },
          "delete": {
            "tags": [
              "last-sentence-controller"
            ],
            "operationId": "delete_1",
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          }
        },
        "/api/bookmark/word": {
          "get": {
            "tags": [
              "bookmark-word-controller"
            ],
            "operationId": "findAllByUserId_2",
            "parameters": [
              {
                "name": "pageable",
                "in": "query",
                "required": true,
                "style": "form",
                "explode": true,
                "schema": {
                  "$ref": "#/components/schemas/Pageable"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "$ref": "#/components/schemas/BookmarkWordResponseDTO"
                      }
                    }
                  }
                }
              }
            }
          }
        },
        "/api/bookmark/sentence": {
          "get": {
            "tags": [
              "bookmark-sentence-controller"
            ],
            "operationId": "findAllByUserId_3",
            "parameters": [
              {
                "name": "pageable",
                "in": "query",
                "required": true,
                "style": "form",
                "explode": true,
                "schema": {
                  "$ref": "#/components/schemas/Pageable"
                }
              }
            ],
            "responses": {
              "200": {
                "description": "OK",
                "content": {
                  "*/*": {
                    "schema": {
                      "type": "array",
                      "items": {
                        "$ref": "#/components/schemas/BookmarkSentenceResponseDTO"
                      }
                    }
                  }
                }
              }
            }
          }
        },
        "/api/member": {
          "delete": {
            "tags": [
              "member-controller"
            ],
            "operationId": "resign",
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "$ref": "#/components/schemas/MemberResignRequestDTO"
                  }
                }
              },
              "required": true
            },
            "responses": {
              "200": {
                "description": "OK"
              }
            }
          }
        },
        "/api/member/login": {
          "post": {
            "tags": [
              "login-endpoint"
            ],
            "requestBody": {
              "content": {
                "application/json": {
                  "schema": {
                    "$ref": "#/components/schemas/member_login_body"
                  }
                }
              }
            },
            "responses": {
              "200": {
                "description": "OK"
              },
              "403": {
                "description": "Forbidden"
              }
            }
          }
        }
      },
      "components": {
        "schemas": {
          "RefreshTokenRequestDTO": {
            "required": [
              "refreshToken"
            ],
            "type": "object",
            "properties": {
              "refreshToken": {
                "type": "string"
              }
            }
          },
          "JwtToken": {
            "type": "object",
            "properties": {
              "accessToken": {
                "type": "string"
              },
              "refreshToken": {
                "type": "string"
              }
            }
          },
          "WordQuizTestProblemResponseDTO": {
            "type": "object",
            "properties": {
              "wordId": {
                "type": "integer",
                "format": "int64"
              },
              "english": {
                "type": "string"
              },
              "koreanChoices": {
                "type": "array",
                "items": {
                  "type": "array",
                  "items": {
                    "type": "string"
                  }
                }
              }
            }
          },
          "WordQuizTestProblemsResponseDTO": {
            "type": "object",
            "properties": {
              "quizId": {
                "type": "integer",
                "format": "int64"
              },
              "wordQuizList": {
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/WordQuizTestProblemResponseDTO"
                }
              }
            }
          },
          "GradeWordQuizTestProblemRequestDTO": {
            "type": "object",
            "properties": {
              "wordId": {
                "type": "integer",
                "format": "int64"
              },
              "userKoreanAnswer": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              }
            }
          },
          "GradeWordQuizTestRequestDTO": {
            "type": "object",
            "properties": {
              "userAnswers": {
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/GradeWordQuizTestProblemRequestDTO"
                }
              }
            }
          },
          "WordQuizProblemResultResponseDTO": {
            "type": "object",
            "properties": {
              "wordId": {
                "type": "integer",
                "format": "int64"
              },
              "english": {
                "type": "string"
              },
              "originalKorean": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "userKoreanAnswer": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "koreanChoices": {
                "type": "array",
                "items": {
                  "type": "array",
                  "items": {
                    "type": "string"
                  }
                }
              },
              "result": {
                "type": "boolean"
              }
            }
          },
          "WordQuizProblemsResultResponseDTO": {
            "type": "object",
            "properties": {
              "quizId": {
                "type": "integer",
                "format": "int64"
              },
              "userId": {
                "type": "integer",
                "format": "int64"
              },
              "correctCount": {
                "type": "integer",
                "format": "int32"
              },
              "totalCount": {
                "type": "integer",
                "format": "int32"
              },
              "wordQuizProblemResultResponseDTOList": {
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/WordQuizProblemResultResponseDTO"
                }
              }
            }
          },
          "SentenceQuizOrderingTestProblem": {
            "type": "object",
            "properties": {
              "sentenceId": {
                "type": "integer",
                "format": "int64"
              },
              "editedEnglish": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "korean": {
                "type": "string"
              }
            }
          },
          "SentenceQuizOrderingTestProblemsResponseDTO": {
            "type": "object",
            "properties": {
              "quizId": {
                "type": "integer",
                "format": "int64"
              },
              "problems": {
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/SentenceQuizOrderingTestProblem"
                }
              }
            }
          },
          "SentenceQuizMeaningTestProblem": {
            "type": "object",
            "properties": {
              "sentenceId": {
                "type": "integer",
                "format": "int64"
              },
              "english": {
                "type": "string"
              },
              "koreanChoices": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              }
            }
          },
          "SentenceQuizMeaningTestProblemsResponseDTO": {
            "type": "object",
            "properties": {
              "quizId": {
                "type": "integer",
                "format": "int64"
              },
              "meaningProblems": {
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/SentenceQuizMeaningTestProblem"
                }
              }
            }
          },
          "GradeSentenceQuizTestProblemRequestDTO": {
            "type": "object",
            "properties": {
              "sentenceId": {
                "type": "integer",
                "format": "int64"
              },
              "userAnswer": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              }
            }
          },
          "GradeSentenceQuizTestRequestDTO": {
            "type": "object",
            "properties": {
              "userAnswers": {
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/GradeSentenceQuizTestProblemRequestDTO"
                }
              }
            }
          },
          "SentenceQuizProblemResult": {
            "type": "object",
            "properties": {
              "sentenceId": {
                "type": "integer",
                "format": "int64"
              },
              "english": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "editedEnglishOrKoreanChoices": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "originalKorean": {
                "type": "string"
              },
              "userAnswer": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "result": {
                "type": "boolean"
              }
            }
          },
          "SentenceQuizProblemsResultResponseDTO": {
            "type": "object",
            "properties": {
              "quizId": {
                "type": "integer",
                "format": "int64"
              },
              "userId": {
                "type": "integer",
                "format": "int64"
              },
              "type": {
                "type": "string",
                "enum": [
                  "MEANING",
                  "ORDERING",
                  "FILLING"
                ]
              },
              "correctCount": {
                "type": "integer",
                "format": "int32"
              },
              "totalCount": {
                "type": "integer",
                "format": "int32"
              },
              "problemResults": {
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/SentenceQuizProblemResult"
                }
              }
            }
          },
          "SentenceQuizFillingTestProblem": {
            "type": "object",
            "properties": {
              "sentenceId": {
                "type": "integer",
                "format": "int64"
              },
              "editedEnglish": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "korean": {
                "type": "string"
              }
            }
          },
          "SentenceQuizFillingTestProblemsResponseDTO": {
            "type": "object",
            "properties": {
              "quizId": {
                "type": "integer",
                "format": "int64"
              },
              "problems": {
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/SentenceQuizFillingTestProblem"
                }
              }
            }
          },
          "MemberRequestDTO": {
            "required": [
              "userEmail"
            ],
            "type": "object",
            "properties": {
              "userEmail": {
                "type": "string"
              },
              "password": {
                "type": "string"
              }
            }
          },
          "MemberChangePasswordRequestDTO": {
            "required": [
              "newPassword",
              "password"
            ],
            "type": "object",
            "properties": {
              "password": {
                "type": "string"
              },
              "newPassword": {
                "type": "string"
              }
            }
          },
          "Pageable": {
            "type": "object",
            "properties": {
              "page": {
                "minimum": 0,
                "type": "integer",
                "format": "int32"
              },
              "size": {
                "minimum": 1,
                "type": "integer",
                "format": "int32"
              },
              "sort": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              }
            }
          },
          "WordResponseDTO": {
            "required": [
              "english"
            ],
            "type": "object",
            "properties": {
              "id": {
                "type": "integer",
                "format": "int64"
              },
              "english": {
                "type": "string"
              },
              "korean": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "bookmarked": {
                "type": "boolean"
              }
            }
          },
          "SentenceResponseDTO": {
            "type": "object",
            "properties": {
              "id": {
                "type": "integer",
                "format": "int64"
              },
              "english": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "korean": {
                "type": "string"
              },
              "isBookmarked": {
                "type": "boolean"
              }
            }
          },
          "RankingResponseDTO": {
            "type": "object",
            "properties": {
              "rank": {
                "type": "integer",
                "format": "int32"
              },
              "userEmail": {
                "type": "string"
              },
              "score": {
                "type": "number",
                "format": "double"
              }
            }
          },
          "MyRankingResponseDTO": {
            "type": "object",
            "properties": {
              "rank": {
                "type": "integer",
                "format": "int64"
              },
              "score": {
                "type": "number",
                "format": "double"
              }
            }
          },
          "WordQuizProblemsResultForAllResponseDTO": {
            "type": "object",
            "properties": {
              "quizId": {
                "type": "integer",
                "format": "int64"
              },
              "correctCount": {
                "type": "integer",
                "format": "int32"
              },
              "totalCount": {
                "type": "integer",
                "format": "int32"
              }
            }
          },
          "WordQuizPracticeProblemResponseDTO": {
            "type": "object",
            "properties": {
              "wordId": {
                "type": "integer",
                "format": "int64"
              },
              "english": {
                "type": "string"
              },
              "originalKorean": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "koreanChoices": {
                "type": "array",
                "items": {
                  "type": "array",
                  "items": {
                    "type": "string"
                  }
                }
              }
            }
          },
          "WordQuizPracticeProblemsResponseDTO": {
            "type": "object",
            "properties": {
              "wordQuizList": {
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/WordQuizPracticeProblemResponseDTO"
                }
              }
            }
          },
          "SentenceQuizProblemsResultForAllResponseDTO": {
            "type": "object",
            "properties": {
              "quizId": {
                "type": "integer",
                "format": "int64"
              },
              "type": {
                "type": "string",
                "enum": [
                  "MEANING",
                  "ORDERING",
                  "FILLING"
                ]
              },
              "correctCount": {
                "type": "integer",
                "format": "int32"
              },
              "totalCount": {
                "type": "integer",
                "format": "int32"
              }
            }
          },
          "SentenceQuizOrderingPracticeProblem": {
            "type": "object",
            "properties": {
              "sentenceId": {
                "type": "integer",
                "format": "int64"
              },
              "originalEnglish": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "shuffledEnglish": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "korean": {
                "type": "string"
              }
            }
          },
          "SentenceQuizOrderingPracticeProblemsResponseDTO": {
            "type": "object",
            "properties": {
              "problems": {
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/SentenceQuizOrderingPracticeProblem"
                }
              }
            }
          },
          "SentenceQuizMeaningPracticeProblem": {
            "type": "object",
            "properties": {
              "sentenceId": {
                "type": "integer",
                "format": "int64"
              },
              "english": {
                "type": "string"
              },
              "koreanChoices": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "originalKorean": {
                "type": "string"
              }
            }
          },
          "SentenceQuizMeaningPracticeProblemsResponseDTO": {
            "type": "object",
            "properties": {
              "meaningProblems": {
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/SentenceQuizMeaningPracticeProblem"
                }
              }
            }
          },
          "SentenceQuizFillingPracticeProblem": {
            "type": "object",
            "properties": {
              "sentenceId": {
                "type": "integer",
                "format": "int64"
              },
              "originalEnglish": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "editedEnglish": {
                "type": "array",
                "items": {
                  "type": "string"
                }
              },
              "korean": {
                "type": "string"
              }
            }
          },
          "SentenceQuizFillingPracticeProblemsResponseDTO": {
            "type": "object",
            "properties": {
              "problems": {
                "type": "array",
                "items": {
                  "$ref": "#/components/schemas/SentenceQuizFillingPracticeProblem"
                }
              }
            }
          },
          "MemberInfoResponseDTO": {
            "required": [
              "userEmail"
            ],
            "type": "object",
            "properties": {
              "userEmail": {
                "type": "string"
              },
              "createdAt": {
                "type": "string",
                "format": "date"
              }
            }
          },
          "LastWordResponseDTO": {
            "type": "object",
            "properties": {
              "wordId": {
                "type": "integer",
                "format": "int64"
              }
            }
          },
          "LastSentenceResponseDTO": {
            "type": "object",
            "properties": {
              "sentenceId": {
                "type": "integer",
                "format": "int64"
              }
            }
          },
          "BookmarkWordResponseDTO": {
            "type": "object",
            "properties": {
              "wordId": {
                "type": "integer",
                "format": "int64"
              }
            }
          },
          "BookmarkSentenceResponseDTO": {
            "type": "object",
            "properties": {
              "sentenceId": {
                "type": "integer",
                "format": "int64"
              }
            }
          },
          "MemberResignRequestDTO": {
            "type": "object",
            "properties": {
              "password": {
                "type": "string"
              }
            }
          },
          "member_login_body": {
            "type": "object",
            "properties": {
              "username": {
                "type": "string"
              },
              "password": {
                "type": "string"
              }
            }
          }
        },
        "securitySchemes": {
          "Token": {
            "type": "http",
            "scheme": "bearer",
            "bearerFormat": "JWT"
          }
        }
      }
    },//url: "https://petstore.swagger.io/v2/swagger.json",
    dom_id: '#swagger-ui',
    deepLinking: true,
    presets: [
      SwaggerUIBundle.presets.apis,
      SwaggerUIStandalonePreset
    ],
    plugins: [
      SwaggerUIBundle.plugins.DownloadUrl
    ],
    layout: "StandaloneLayout"
  });

  //</editor-fold>
};
