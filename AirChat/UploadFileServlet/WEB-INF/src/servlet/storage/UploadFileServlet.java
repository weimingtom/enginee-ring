/**
 * 
 */
package servlet.storage;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


/**
 *アップロード用サーバー
 */
public class UploadFileServlet extends HttpServlet {

	/**
	 * アップロード用サーバーのURL
	 */
	private final String myServletURL = "http://www.サーバーURL/FileUploader";
	/**
	 * アップロードしたファイルを格納するパス
	 * ダウンロードするパス
	 */
	private final String myDownloadPath = myServletURL + "/files";
	/**
	 * 初期処理
	 */
	@Override
	public void init()	throws ServletException {
		  System.out.println("------------UploadFileServlet Start------------");
	}

	/**
	 * POSTにて、ファイルアップロードを行う
	 * 尚、ファイル名は日本語をアップロードすると、その後、TOMCATの性質上、ダウンロードできなくなるので
	 * ファイル名を変更する。
	 */
	@Override
	public void doPost(HttpServletRequest req,HttpServletResponse res) 
		throws ServletException, IOException {

		//データID取得
		String id = req.getParameter("id");
		
        // (1)アップロードファイルを格納するPATHを取得
		String path = getServletContext().getRealPath("files");
        // (2)ServletFileUploadオブジェクトを生成
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

        // (3)アップロードする際の基準値を設定
		factory.setSizeThreshold(1024);
		upload.setSizeMax(-1);
		//upload.setHeaderEncoding("Windows-31J");
		upload.setHeaderEncoding("UTF-8");

		// 溜めたファイル名が格納されるリスト
		List<String> fileNameList = new ArrayList<String>();

		try {
            //(4)ファイルデータ(FileItemオブジェクト)を取得し、
            //   Listオブジェクトとして返す
			List list = upload.parseRequest(req);

            // (5)ファイルデータ(FileItemオブジェクト)を順に処理
			Iterator iterator = list.iterator();
			while(iterator.hasNext()){
				FileItem fItem = (FileItem)iterator.next();

				//(6)ファイルデータの場合、if内を実行
				if(!(fItem.isFormField())){
					//(7)ファイルデータのファイル名(PATH名含む)を取得
					String fileName = fItem.getName();
					if((fileName != null) && (!fileName.equals(""))){
						//(8)PATH名を除くファイル名のみを取得
						fileName=(new File(fileName)).getName();
						// 日本語無しの名前へ変換する
						String newfileName = generateFileName(id,fileName);
						//(9)ファイルデータを指定されたファイルに書き出し
						fItem.write(new File(path + "/" + newfileName));
						// ファイル名の格納
						fileNameList.add(newfileName);
					}
				}
			}
		}catch (FileUploadException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		// XML文書として、ストレージに配置したファイルの絶対パスをレスポンスに設定する
		setXmlResponse(res,fileNameList,myDownloadPath);
	}

	/**
	 * ゲットした場合、引数に「ファイル名」を渡すことにより、変換したファイル名を返す
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String dateID = req.getParameter("id");
		if ( dateID == null || "".equals(dateID) ) {
			System.err.println("不正アクセスすんな");
			return;
		}
		List<String> nameList = new ArrayList<String>();
		
		String dlfile = searchFile(dateID);
		
		if (dlfile == null || "".equals(dlfile)) {
			return;
		}
		nameList.add(dlfile);
		// XML文書として、ストレージに配置したファイルの絶対パスをレスポンスに設定する
		setXmlResponse(res,nameList,myDownloadPath);
	}
	
	/**
	 * ファイル検索
	 * @param aId 検索するファイルのID
	 * @return 検索したファイル名
	 */
	private String searchFile(String aId) {
		String path = getServletContext().getRealPath("/");
	    String filepath = path+"/files";
	    File dir = new File(filepath);
	    File[] files = dir.listFiles();
	    for ( File file : files ) {
	    	String name = file.getName();
	    	if ( name.indexOf(aId) == -1) {
	    		continue;
	    	}
	    	return name;
	    }
	    return null;
	}
	/**
	 * ファイル名の変換
	 * @param aId データID
	 * @param aFileName ユーザのアップロードしたファイル名
	 * @return ファイル名
	 */
	private String generateFileName(String aId,String aFileName) {
		// 拡張子を取得する
		String[] splitStr = aFileName.split("\\.");
		String type = splitStr[splitStr.length -1];
		// IDに拡張子をつける
		String newFileName = aId+"."+type;
		
		return newFileName;
	}
	
	/**
	 * リクエストに対して、ストレージに溜め込んだファイルの情報をXML形式で返す
	 * @param res HttpServletResponse
	 * @param fileList ファイルリスト
	 * @param path ストレージのパス
	 */
	private void setXmlResponse(HttpServletResponse res , List<String> fileList ,String path) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			DOMImplementation domImpl=builder.getDOMImplementation();
			// xml　ドキュメントの生成
			Document document = domImpl.createDocument("","fileList",null);
			Element root = document.getDocumentElement();
			for ( String file : fileList ) {
				// 新たにエレメントを追加
				Element fileElm = document.createElement("file");

				// 種類。（IMG or DATA)
				Element typeElm = document.createElement("type");
				// 拡張子を取得する
				String[] splitStr = file.split("\\.");
				String wktype = splitStr[splitStr.length -1];
				String resultType = "DATA";
				// 拡張子の確認
				if ( EImgExtension.isImg(wktype) ) {
					resultType = "IMG";
				}
				typeElm.appendChild(document.createTextNode(resultType));
				fileElm.appendChild(typeElm);

				// ファイル名
				Element fileNameElm = document.createElement("fileName");
				fileNameElm.appendChild(document.createTextNode(file));
				fileElm.appendChild(fileNameElm);
				// ダウンロード用のパス
				Element downloadElm = document.createElement("download");
				downloadElm.appendChild(document.createTextNode(path + "/"+file));
				fileElm.appendChild(downloadElm);

				root.appendChild(fileElm);
			}

			// dom生成
			DOMSource source = new DOMSource(document);
			// 以下、レスポンスに設定する
			res.setContentType("text/xml;charset=UTF-8");
			OutputStream out = res.getOutputStream(); 
			StreamResult sr = new StreamResult(out); 
			//出力
			TransformerFactory transFactory = TransformerFactory.newInstance();
			Transformer transformer = transFactory.newTransformer();
			transformer.transform(source, sr);

			out.close();

		} catch ( Exception ex) {
			System.out.println("例外"+ex);
		}
	}
}
