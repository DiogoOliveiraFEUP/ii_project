#include "stdio.h"
#include "string.h"

int main(){
    char elements[8192], element_type[7][64], nr[4], intermediate[4096], aux_i[128], send[64], aux_s[1024];
    char *token;
    int i=0, j=0;


    FILE *f_PO, *f_send;
    
    

    strcpy(&element_type[0][0], "Wi"); //2
    strcpy(&element_type[1][0], "Wo"); //2
    strcpy(&element_type[2][0], "M"); //8
    strcpy(&element_type[3][0], "L"); //10
    strcpy(&element_type[4][0], "R"); //16
    strcpy(&element_type[5][0], "P"); //3
    strcpy(&element_type[6][0], "O"); //3

    elements[0] = '\0';


    f_send = fopen("send.txt", "w");

        for (i=0; i<7;i++){ //element type
            
            intermediate[0] = '\0';
            for(j=1; j< 17;j++){ //element name

                
                
                aux_i[0] = '\0';
                aux_s[0] = '\0';
                strcpy(aux_i, &element_type[i][0]);

                sprintf(nr, "%d", j); //printf("--------%s\n", nr);
                strcat(aux_i,nr);

                strcat(aux_i, "_");

                if( i== 0){ //wi
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "Receive_R,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "Receive_L,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);
                }
                else if (i == 1){ //wo
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "Send2R,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "Send2L,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);


                } 

                 else if (i == 2){ //M
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "SendR2R,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendR2L,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendL2L,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendL2R,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    //sprintf(send, "ChangeTool,");
                    //strcat(aux_s, aux_i);
                    //strcat(aux_s, send);

                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);


                } 
                else if (i == 3){ //L
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "SendR2L,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendL2R,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);


                } 
                 else if (i == 4){ //R
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "SendR2L,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendR2U,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendR2D,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendL2R,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendL2U,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendL2D,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendU2R,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendU2L,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendU2D,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendD2R,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendD2L,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendD2U,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    //sprintf(send, "ChangeTool,");
                    //strcat(aux_s, aux_i);
                    //strcat(aux_s, send);

                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);


                } 
                 else if (i == 5){ //P
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "SendU2D,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendD2U,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);
                    
                    sprintf(send, "SendPusher,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);


                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);


                } 
                /* else if (i == 6){ //P
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "SendU2D,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendD2U,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);
                    
                    sprintf(send, "SendPusher,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);


                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);


                } */

               if( (j == 16 && i == 4) || (j == 2 && i == 0) || (j == 2 && i == 1) || (j == 8 && i == 2) || (j == 10 && i == 3) || (j == 3 && i == 5) || (j == 3 && i == 6) ){
                    break; //no more of that machine type
                }

                //all send types hardcoded
            }

            if(i==0 && j == 0){
                    strcpy(elements, intermediate);
                    }
                else{
                    strcat(elements, intermediate);
                }


                //if(i == 6 && j == 3){}
                //else{strcat(elements, ",");} 
        }
        strcat(elements, " : BOOL;");
        //printf("%s\n", elements);
        fputs("\nDECLARATION------------\n", f_send);
        fputs(elements, f_send);

    fclose(f_send);

    f_PO = fopen("PO.txt", "w");
    if(f_PO == NULL){
        printf("nao consegui escrever ficheiro txt PO\n");
    }

    elements[0] = '\0';


    f_send = fopen("send.txt", "w");

        for (i=0; i<7;i++){ //element type
            
            intermediate[0] = '\0';
            for(j=1; j< 17;j++){ //element name

                
                
                aux_i[0] = '\0';
                aux_s[0] = '\0';
                strcpy(aux_i, &element_type[i][0]);

                sprintf(nr, "%d", j); //printf("--------%s\n", nr);
                strcat(aux_i,nr);

                strcat(aux_i, "_");

               /* if( i== 0){ //wi
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "Receive_R,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "Receive_L,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);
                }*/
                 if (i == 1){ //wo
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "PushO_R,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "PushO_L,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);


                } 

                 else if (i == 2){ //M
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "PushO_R,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "PushO_L,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    //sprintf(send, "ChangeTool,");
                    //strcat(aux_s, aux_i);
                    //strcat(aux_s, send);

                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);


                } 
                else if (i == 3){ //L
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "PushO_R,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "PushO_L,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);


                } 
                 else if (i == 4){ //R
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "PushO_R,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "PushO_L,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "PushO_U,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "PushO_D,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    //sprintf(send, "ChangeTool,");
                    //strcat(aux_s, aux_i);
                    //strcat(aux_s, send);

                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);


                } 
                 else if (i == 5){ //P
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "PushO_U,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "PushO_D,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);
                    
                    sprintf(send, "PushO_P,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);


                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);


                } 
                /* else if (i == 6){ //P
                    //strcpy(aux_i, intermediate);
                    
                    sprintf(send, "SendU2D,");

                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);

                    sprintf(send, "SendD2U,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);
                    
                    sprintf(send, "SendPusher,");
                    strcat(aux_s, aux_i);
                    strcat(aux_s, send);


                    strcat(intermediate, aux_s);
                    //printf("%s--------------\n", intermediate);


                } */

               if( (j == 16 && i == 4) || (j == 2 && i == 0) || (j == 2 && i == 1) || (j == 8 && i == 2) || (j == 10 && i == 3) || (j == 3 && i == 5) || (j == 3 && i == 6) ){
                    break; //no more of that machine type
                }

                //all send types hardcoded
            }

            if(i==0 && j == 0){
                    strcpy(elements, intermediate);
                    }
                else{
                    strcat(elements, intermediate);
                }


                //if(i == 6 && j == 3){}
                //else{strcat(elements, ",");} 
        }
        strcat(elements, " : BOOL;");
        printf("%s\n", elements);
        fputs("\nDECLARATION------------\n", f_PO);
        fputs(elements, f_PO);

    
    fclose(f_PO); 


    return 0;
}