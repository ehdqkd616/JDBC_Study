-- ������ ����
-- �����ͺ��̽��� ������ ������ ����ϴ� ���� ���� ����
-- �����ͺ��̽��� ���� ��� ���Ѱ� å���� ������ ����

-- ����� ����(�Ϲ� ����)
-- �����ͺ��̽��� ���Ͽ� ����(Query), ����, ���� �ۼ� ���� �۾��� ������ �� �ִ� ����
-- ������ ���� ������ �ʿ��� �ּ����� ���Ѹ� ������ ���� ��Ģ���� ��
CREATE USER KH IDENTIFIED BY KH;
--      ����� ���� �̸�       ����� ���� ��й�ȣ
GRANT RESOURCE, CONNECT TO KH;

--  ���̺� : �����ͺ��̽� ������ ��� �����ʹ� ���̺� ����
--          ��� Į������ �����Ǵ� ���� �⺻���� �����ͺ��̽� ��ü

CREATE USER MEMBER IDENTIFIED BY MEMBER;
GRANT RESOURCE, CONNECT TO MEMBER;


CREATE USER BOARD IDENTIFIED BY BOARD;
GRANT RESOURCE, CONNECT TO BOARD;

REVOKE RESOURCE, CONNECT FROM BOARD;
DROP USER BOARD;