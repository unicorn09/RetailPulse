package com.unicorn.retailpulse;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class EuclidianDistance {
    public static final String TAG="Eucldist";
    private float[][] data=new float[1][16];

    Double ret1[][];
    int label[];
//    double ret1[][]={{-3.04255634546279E-04,4.29576225578784E-02,3.90035212039947E-01,-3.88301685452461E-02,-4.46470648050308E-01,-2.02628821134567E-01,-6.75956020131707E-04,6.93894103169441E-02,3.02215367555618E-01,4.21197742223739E-01,-2.60506719350814E-01,-1.83521792292594E-01,-6.37527182698249E-02,-3.35860282182693E-01,-3.33857685327529E-01,-2.63874381780624E-02
//    },{1.91125214099884E-01,1.40297919511795E-01,-1.80141299962997E-01,3.26126754283905E-01,8.82204622030258E-02,-2.01102837920188E-01,-3.69547426700592E-01,-5.84820769727230E-02,2.28451371192932E-01,-2.39606857299804E-01,3.50824803113937E-01,-2.83128023147583E-01,3.74719113111495E-01,2.22889557480812E-02,3.31708401441574E-01,-2.30184182524681E-01
//    },{3.10109630227088E-02,3.20877460762858E-03,3.53288829326629E-01,-3.22153270244598E-02,-4.0104192495346E-01,-1.74115300178527E-01,4.69373427331447E-02,2.74077244102954E-02,3.01906287670135E-01,4.29440259933471E-01,-3.0417850613594E-01,-1.4603154361248E-01,-9.72277820110321E-02,-4.04559105634689E-01,-3.37053477764129E-01,5.41250109672546E-02
//    },{3.07544887065887E-01,-3.58726650476455E-01,-2.14388012886047E-01,2.18433942645788E-02,2.41682186722755E-01,3.34924727678298E-01,2.77471125125885E-01,-3.17820698022842E-01,1.56939387321472E-01,-1.18179984390735E-01,-8.36274176836013E-02,3.0685007572174E-01,-2.53973990678787E-01,-2.69938170909881E-01,-8.18080827593803E-02,3.12886297702789E-01
//    },{2.79039621353149E-01,-3.62671971321105E-01,-1.82683199644088E-01,2.33076396398246E-03,2.27415934205055E-01,3.51159483194351E-01,2.83561170101165E-01,-3.02716314792633E-01,1.73538252711296E-01,-9.01425033807754E-02,-9.80452969670295E-02,3.12527120113372E-01,-2.77018815279006E-01,-2.65576601028442E-01,-9.43450033664703E-02,3.29322874546051E-01
//    },{3.00973147153854E-01,-3.59263569116592E-01,-2.13046580553054E-01,6.83227414265275E-03,2.37345412373542E-01,3.38689565658569E-01,2.85049080848693E-01,-3.14354062080383E-01,1.54091507196426E-01,-1.16461724042892E-01,-9.08045470714569E-02,3.10731470584869E-01,-2.59792983531951E-01,-2.63214826583862E-01,-8.33131447434425E-02,3.13004255294799E-01
//    },{2.18568339943885E-01,6.87892735004425E-02,-2.83820152282714E-01,2.94926643371582E-01,2.15006306767463E-01,-8.81066918373107E-02,-3.26504617929458E-01,-8.8854432106018E-02,1.98296636343002E-01,-3.09071391820907E-01,3.79956275224685E-01,-2.14256688952445E-01,3.34518373012542E-01,5.49167804419994E-02,3.69215369224548E-01,-1.8738117814064E-01
//    },{1.36851876974105E-01,1.62504121661186E-01,-1.74177631735801E-01,3.01339626312255E-01,8.51124301552772E-02,-2.14674517512321E-01,-3.89129042625427E-01,1.36793300043791E-03,2.17311725020408E-01,-2.1541254222393E-01,3.4850987792015E-01,-2.95544415712356E-01,3.85653734207153E-01,5.16256392002105E-02,3.28468352556228E-01,-2.59325832128524E-01
//    },{-4.46816235780715E-02,6.50666654109954E-02,3.9821383357048E-01,-6.67075514793395E-02,-4.48939740657806E-01,-1.99502289295196E-01,1.31956310942769E-02,1.02002501487731E-01,2.50657796859741E-01,4.37741607427597E-01,-2.76742875576019E-01,-1.7301107943058E-01,-6.40906766057014E-02,-3.11466664075851E-01,-3.40941876173019E-01,-2.21852120012044E-02
//    },{2.53139346837997E-01,-2.51037739217281E-02,-3.50762873888015E-01,2.7066358923912E-01,3.1703370809555E-01,1.8683984875679E-02,-2.53888428211212E-01,-1.68237760663032E-01,1.81620255112648E-01,-3.76270294189453E-01,3.65871161222457E-01,-1.05139702558517E-01,2.70026355981826E-01,5.35236857831478E-02,3.72932940721511E-01,-1.05046272277832E-01
//    },{-1.33854113519191E-02,4.68030758202075E-02,3.95879298448562E-01,-5.37460558116436E-02,-4.47439640760421E-01,-1.96227893233299E-01,4.93219261988997E-03,8.46555829048156E-02,2.84259289503097E-01,4.27407562732696E-01,-2.68940031528472E-01,-1.79502114653587E-01,-5.68573661148548E-02,-3.23156028985977E-01,-3.40577542781829E-01,-2.08987481892108E-02
//    },{1.10866054892539E-01,-1.88178196549415E-02,3.45454841852188E-01,5.8605134487152E-02,-3.91255378723144E-01,-1.1921714246273E-01,2.28138994425535E-02,-3.03367841988801E-02,4.17704671621322E-01,3.98053169250488E-01,-2.42711588740348E-01,-1.3427497446537E-01,-5.98994903266429E-02,-4.15480315685272E-01,-3.29124391078948E-01,3.80764193832874E-02
//    },{2.47402474284172E-01,-3.63846898078918E-01,-1.94983020424842E-01,-5.4200354963541E-02,2.52502769231796E-01,3.66154879331588E-01,2.91208654642105E-01,-2.80074208974838E-01,1.08667708933353E-01,-9.87878367304801E-02,-1.13022126257419E-01,3.3667454123497E-01,-2.72114515304565E-01,-2.16639697551727E-01,-1.05979278683662E-01,3.52814555168151E-01
//    },{2.95115381479263E-01,-3.61186206340789E-01,-1.76069095730781E-01,1.83485187590122E-02,2.09083154797554E-01,3.40014040470123E-01,2.81302392482757E-01,-3.10156017541885E-01,1.73919424414634E-01,-7.84556716680526E-02,-1.14549860358238E-01,2.99270212650299E-01,-2.84224510192871E-01,-2.82167017459869E-01,-1.12546347081661E-01,3.22963148355484E-01
//    },{2.95388907194137E-01,-3.48895698785781E-01,-1.94458305835723E-01,-3.74370254576206E-02,2.36780047416687E-01,3.51002812385559E-01,2.90760934352874E-01,-2.98707634210586E-01,1.53827428817749E-01,-9.64285880327224E-02,-1.04598730802536E-01,3.16120773553848E-01,-2.64601051807403E-01,-2.52551943063735E-01,-1.07677213847637E-01,3.30969095230102E-01
//    },{1.45354509353637E-01,-9.12026390433311E-02,3.17645192146301E-01,3.68548370897769E-02,-3.64173233509063E-01,-1.14155337214469E-01,4.34180237352848E-02,-4.05078195035457E-02,4.07943457365036E-01,3.7923526763916E-01,-2.61874765157699E-01,-1.04502126574516E-01,-7.77089148759841E-02,-4.64106559753417E-01,-3.23637962341308E-01,5.66256940364837E-02
//    },{1.59401968121528E-02,1.990707218647E-02,3.71939152479171E-01,-1.77006516605615E-02,-4.24222499132156E-01,-1.85312256217002E-01,3.20083871483802E-02,4.39819395542144E-02,2.93848752975463E-01,4.2170175909996E-01,-2.89253890514373E-01,-1.57680198550224E-01,-7.85638317465782E-02,-3.80991280078887E-01,-3.44982445240020E-01,1.95837039500474E-02
//    },{-7.27594690397381E-03,4.31079044938087E-02,3.90814095735549E-01,-4.89211641252040E-02,-4.42512273788452E-01,-1.99190214276313E-01,4.02256427332758E-03,7.5048305094242E-02,2.95137882232666E-01,4.23607915639877E-01,-2.62497782707214E-01,-1.81229025125503E-01,-6.88882619142532E-02,-3.34908217191696E-01,-3.40836465358734E-01,-2.09737941622734E-02
//    },{3.09276342391967E-01,-3.58357518911361E-01,-2.18484058976173E-01,2.72255335003137E-02,2.45237693190574E-01,3.32628190517425E-01,2.72985726594924E-01,-3.15963000059127E-01,1.62511944770812E-01,-1.23893588781356E-01,-7.92533233761787E-02,3.05104434490203E-01,-2.51972794532775E-01,-2.72679060697555E-01,-7.83155858516693E-02,3.1178143620491E-01
//    },{1.5678833425045E-01,1.49603873491287E-01,-1.85387820005416E-01,3.04541438817977E-01,8.70050638914108E-02,-2.09224164485931E-01,-3.77088367938995E-01,-3.0812008306384E-02,2.13130116462707E-01,-2.31184422969818E-01,3.48603546619415E-01,-2.95906603336334E-01,3.90120029449462E-01,4.77309301495552E-02,3.28159362077713E-01,-2.47620597481727E-01
//    },{2.99148708581924E-01,2.01311409473419E-02,-2.18312799930572E-01,3.58869075775146E-01,1.21372133493423E-01,-1.15781493484973E-01,-3.10177415609359E-01,-1.66389316320419E-01,3.29565525054931E-01,-2.46873974800109E-01,3.43732237815856E-01,-1.96663543581962E-01,3.2594609260559E-01,-7.60193243622779E-02,3.37797999382019E-01,-1.7061473429203E-01
//    },{2.63294130563735E-01,-3.55968981981277E-01,-1.7004545032978E-01,-3.05470414459705E-02,2.12577536702156E-01,3.4972408413887E-01,3.04897725582122E-01,-2.89073556661605E-01,1.35713338851928E-01,-6.79820254445075E-02,-1.29133865237236E-01,3.19053351879119E-01,-2.93892413377761E-01,-2.63025224208831E-01,-1.18745230138301E-01,3.44856262207031E-01
//    },{2.45324030518531E-01,-1.56760830432176E-02,-3.40283036231994E-01,2.79051810503005E-01,3.06144028902053E-01,7.70539138466119E-03,-2.60098367929458E-01,-1.55375435948371E-01,1.78612485527992E-01,-3.74742776155471E-01,3.70459556579589E-01,-1.18451714515686E-01,2.80268788337707E-01,5.83591572940349E-02,3.76466244459152E-01,-1.14205360412597E-01
//    },{3.52519780397415E-01,-6.07171021401882E-02,-3.42794775962829E-01,2.89220958948135E-01,2.95684903860092E-01,4.34885285794734E-02,-1.95616796612739E-01,-2.28087782859802E-01,2.57512718439102E-01,-3.57292115688323E-01,3.41500908136367E-01,-9.28547605872154E-02,2.2681039571762E-01,-4.25191037356853E-02,3.38366270065307E-01,-6.57094717025756E-02
//    },{-5.06246238946914E-02,4.13171388208866E-02,3.91689002513885E-01,-8.32573845982551E-02,-4.34255272150039E-01,-1.66786149144172E-01,6.65082782506942E-02,9.24831181764602E-02,2.28646129369735E-01,4.42322939634323E-01,-3.02034884691238E-01,-1.2720139324665E-01,-1.1489176005125E-01,-3.11619102954864E-01,-3.74411642551422E-01,1.34762655943632E-02
//    },{-4.99554052948951E-02,6.46779090166091E-02,4.08980309963226E-01,-5.74369356036186E-02,-4.40313279628753E-01,-1.75763696432113E-01,3.31376232206821E-02,9.33641046285629E-02,2.49161958694458E-01,4.39234524965286E-01,-2.93551981449127E-01,-1.43603459000587E-01,-7.60315507650375E-02,-3.04219901561737E-01,-3.57333809137344E-01,-1.87729969620704E-02
//    },{-4.99554052948951E-02,6.46779090166091E-02,4.08980309963226E-01,-5.74369356036186E-02,-4.40313279628753E-01,-1.75763696432113E-01,3.31376232206821E-02,9.33641046285629E-02,2.49161958694458E-01,4.39234524965286E-01,-2.93551981449127E-01,-1.43603459000587E-01,-7.60315507650375E-02,-3.04219901561737E-01,-3.57333809137344E-01,-1.87729969620704E-02
//    },{2.46676623821258E-01,-3.65767747163772E-01,-1.35100588202476E-01,-2.28121895343065E-02,1.87093049287796E-01,3.44101130962371E-01,3.04832845926284E-01,-2.85445868968963E-01,1.63322031497955E-01,-3.65179888904094E-02,-1.34993374347686E-01,3.26080352067947E-01,-2.95813769102096E-01,-2.79814034700393E-01,-1.28520101308822E-01,3.51997166872024E-01
//    },{2.07775369286537E-01,-3.4954634308815E-01,-8.46579894423484E-02,-6.26640766859054E-02,1.39458671212196E-01,3.28955620527267E-01,3.36204200983047E-01,-2.4427007138729E-01,1.2760442495346E-01,1.93894710391759E-02,-1.9407071173191E-01,3.28691780567169E-01,-3.3250093460083E-01,-2.79136955738067E-01,-2.02520027756690E-01,3.62411320209503E-01
//    },{6.78650811314582E-02,-6.00795969367027E-02,3.59620720148086E-01,-5.10099902749061E-03,-3.86461824178695E-01,-1.13264352083206E-01,6.91418498754501E-02,-1.14557044580578E-02,3.39204847812652E-01,4.06778156757354E-01,-2.96704500913619E-01,-1.050246655941E-01,-1.14455170929431E-01,-4.15724098682403E-01,-3.56892764568328E-01,4.94690127670764E-02
//    },{2.89961397647857E-01,-3.63160997629165E-01,-1.65862649679183E-01,1.0031116195023E-02,1.95118710398674E-01,3.36451083421707E-01,2.8805536031723E-01,-3.05533617734909E-01,1.73145577311515E-01,-6.65034279227256E-02,-1.26460179686546E-01,2.98998415470123E-01,-2.93497711420059E-01,-2.86473751068115E-01,-1.23518511652946E-01,3.24913471937179E-01
//    },{-1.16982106119394E-02,2.6421757414937E-02,3.98022711277008E-01,-4.65447716414928E-02,-4.26827520132064E-01,-1.47902905941009E-01,5.98925612866878E-02,6.15873150527477E-02,2.75806903839111E-01,4.38275724649429E-01,-3.07241588830947E-01,-1.23217232525348E-01,-1.11836172640323E-01,-3.31411182880401E-01,-3.56278002262115E-01,1.13937333226203E-02
//    },{2.9462906718254E-01,-3.61819505691528E-01,-1.77313521504402E-01,1.82320047169923E-02,2.06548750400543E-01,3.39559108018875E-01,2.81893968582153E-01,-3.08662295341491E-01,1.70462787151336E-01,-7.64900669455528E-02,-1.18112348020076E-01,3.00475478172302E-01,-2.85505563020706E-01,-2.81448662281036E-01,-1.15133121609687E-01,3.23545724153518E-01
//    }};
//
//    int label[]={2,1,2,0,0,0,1,1,2,1,2,2,0,0,0,2,2,2,0,1,1,0,1,1,2,2,0,0,2,0,2,0};


    private Context context;
    public EuclidianDistance(Context context,float  ret[][] )
    {
        this.context=context;
        data=ret;

    }



    //it will calculate min distance and return label
    public int calculatedis(float dis[][])
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Gson gson = new Gson();
        String json = sharedPrefs.getString(TAG, "");
        Type type = new TypeToken<List<Double>>() {}.getType();
        List<Double> arrayList = gson.fromJson(json, type);


        Data d=new Data(context);
        ret1=d.getArray();
        label=d.getLabel();

        double min=Double.MAX_VALUE;
        int min_index=0;
        for(int i=0;i<32;i++)
        {
            double sum=0;
            for(int j=0;j<16;j++)
            {
                double p=dis[0][j];
                double q=ret1[i][j];
                sum+=((p-q)*(p-q));
            }
            double sumsqrt=Math.sqrt(sum);
            if(sumsqrt<min)
            {min=sumsqrt;min_index=i;}

        }
        //Log.e(TAG, "calculatedis: "+label[min_index]);
       // Log.e(TAG, "calculatedis: "+min_index);
        return label[min_index];
    }


}
