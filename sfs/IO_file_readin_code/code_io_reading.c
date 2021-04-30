#include "stdio.h"
#include "string.h"

int main(){
    char linha[256], var_codesys[64], GVL_Inputs[2048], GVL_Outputs[2048], GVL_Regs[64];
    char *token;
    int i=0, inputs=0, outputs=0, excecao=0, cnt_i=0,cnt_o=0,cnt_r=0;


    FILE *f_exc, *f_codesys, *f_coluna_vars_i, *f_coluna_vars_o, *f_coluna_vars_r;
    
    f_exc = fopen("io_c_var_names_final.txt", "r");
    if(f_exc == NULL){
        printf("nao consegui abrir ficheiro txt exportado do excel\n");
    }

    f_coluna_vars_i = fopen("excel_nome_de_coluna_vars_i.txt", "w");
    f_coluna_vars_o = fopen("excel_nome_de_coluna_vars_o.txt", "w");
    f_coluna_vars_r = fopen("excel_nome_de_coluna_vars_r.txt", "w");


    //GVL_Outputs[0] = ' ';
    //GVL_Inputs[0] = ' ';
    //GVL_Regs[0] = ' ';

    while( fgets(linha, 256, f_exc) != NULL ){ //nao EOF

        //puts(linha);
        //printf("%d\n", i++);
        token = NULL;
        token = strtok(linha, "***"); // token 0 
        if(token == NULL){printf("NULL\n"); return -1;}


        token = strtok(NULL, "***"); //token 1 o pretendido c nome do IO
        if(token == NULL){printf("NULL\n"); return -1;}

        strcpy(var_codesys, token); //nao esquecer q nome tem \n no fim
        //printf("\t %ld\n", strlen(var_codesys));
        //printf( "----%c\n",var_codesys[strlen(var_codesys)-2]);
        
        if( strchr(var_codesys, 'I') != NULL ){ //input
            inputs++;
            //puts("Input: ");
            var_codesys[strlen(var_codesys)-1] = '\0';
            
            fputs("Application.GVL.",f_coluna_vars_i);
            fputs(var_codesys,f_coluna_vars_i);
            fputc('\n',f_coluna_vars_i);
            cnt_i++;
            if(cnt_i == 8){
                cnt_i=0;
                fputc('\n',f_coluna_vars_i);
            }

            var_codesys[strlen(var_codesys)-2] = ','; //troca \n por ,
            //var_codesys[strlen(var_codesys)-1] = '\0';
            //puts(var_codesys);
            strcat(GVL_Inputs, var_codesys);
            
        }
        else if( strchr(var_codesys, 'O') != NULL ){//output
            outputs++;
            //puts("output: ");
            var_codesys[strlen(var_codesys)-1] = '\0';

            fputs("Application.GVL.",f_coluna_vars_o);
            fputs(var_codesys,f_coluna_vars_o);
            fputc('\n',f_coluna_vars_o);
            cnt_o++;
            if(cnt_o == 8){
                cnt_o=0;
                fputc('\n',f_coluna_vars_o);
            }

            var_codesys[strlen(var_codesys)-2] = ',';
            //var_codesys[strlen(var_codesys)-1] = '\0';
            //puts(var_codesys);
            strcat(GVL_Outputs, var_codesys);
        }
        else{//regs
            excecao++;
            //puts("excessao: ");
            var_codesys[strlen(var_codesys)-1] = '\0';

            fputs("Application.GVL.",f_coluna_vars_r);
            fputs(var_codesys,f_coluna_vars_r);
            fputc('\n',f_coluna_vars_r);
            cnt_r++;
            if(cnt_r == 8){
                cnt_r=0;
                fputc('\n',f_coluna_vars_r);
            }

            var_codesys[strlen(var_codesys)-2] = ',';
            //var_codesys[strlen(var_codesys)-1] = '\0';
            //puts(var_codesys);
            strcat(GVL_Regs, var_codesys);


        } 


    }
    printf("O=%d I=%d E=%d\n", outputs, inputs, excecao);
    

    fclose(f_exc);

    GVL_Outputs[strlen(GVL_Outputs)-1] = ' ';
    GVL_Inputs[strlen(GVL_Inputs)-1] = ' ';
    GVL_Regs[strlen(GVL_Regs)-1] = ' ';

    strcat(GVL_Outputs, " :BOOL;");
    strcat(GVL_Inputs, " :BOOL;");
    strcat(GVL_Regs, " :BOOL;");

    //printf("\t%s\n", GVL_Outputs);
    //printf("\t%s\n", GVL_Inputs);
    //printf("\t%s\n", GVL_Regs);

    f_codesys = fopen("codesys_vars_decl.txt", "w");
        fputs("\nOUTPUTS DECLARATION------------\n", f_codesys);
        fputs(GVL_Outputs, f_codesys);
        fputs("\nINPUTS DECLARATION------------\n", f_codesys);
        fputs(GVL_Inputs, f_codesys);
        fputs("\nREGS DECLARATION------------\n", f_codesys);
        fputs(GVL_Regs, f_codesys);

    fclose(f_codesys);
    
    

    fclose(f_coluna_vars_i);
    fclose(f_coluna_vars_o);
    fclose(f_coluna_vars_r);

    return 0;
}